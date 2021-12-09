package ru.sbt.subsidy.dictionaries.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.sbt.subsidy.dictionaries.api.ErrorResponse;
import ru.sbt.subsidy.dictionaries.dao.Dictionary;
import ru.sbt.subsidy.dictionaries.dao.DictionaryItem;
import ru.sbt.subsidy.dictionaries.dao.DictionaryItemCriteriaFilter;
import ru.sbt.subsidy.dictionaries.dao.DictionaryItemRepository;
import ru.sbt.subsidy.dictionaries.dao.DictionaryRepository;
import ru.sbt.subsidy.dictionaries.exceptions.DataNotFoundException;
import ru.sbt.subsidy.dictionaries.exceptions.QueryExecutionException;
import ru.sbt.subsidy.dictionaries.exceptions.SubsidyException;
import ru.sbt.subsidy.dictionaries.exceptions.ValidationException;
import ru.sbt.subsidy.dictionaries.services.InnerDictionariesService;
import ru.sbt.subsidy.dictionaries.services.RsqlParseService;
import ru.sbt.subsidy.dictionaries.util.CriteriaPredicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class InnerDictionariesServiceImpl implements InnerDictionariesService {

  private static final String DICTIONARY_ENTRY_NOT_FOUND = "dictionary.entry.not.found";
  private static final String DICTIONARY_SCHEMA_NOT_FOUND = "dictionaries.schema.not.found";
  private static final String CANNOT_CONVERT_TO_OBJECT_NODE = "cannot.convert.to.object.node";
  private static final String VALIDATION_EXCEPTION = "validation.exception";
  private static final String VALIDATION_WARNING = "validation.warning";
  private static final String QUERY_EXECUTION_EXCEPTION = "query.execution.exception";


  private final DictionaryItemRepository dictionaryItemRepository;
  private final DictionaryRepository dictionaryRepository;
  private final JsonSchemaFactory factory;
  private final RsqlParseService rsqlParseService;


  @Override
  public List<JsonNode> query(String dictionary, String locale, String queryParams) {
    List<CriteriaPredicate> parsedParams = Collections.emptyList();
    if (!StringUtils.isEmpty(queryParams)) {
      parsedParams = rsqlParseService.parse(queryParams, ",");
    }

    Specification<DictionaryItem> filter = DictionaryItemCriteriaFilter.getComplexDictItemSpec(
        dictionary, locale, parsedParams);

    List<DictionaryItem> dictionaryItems;
    try {
      dictionaryItems = dictionaryItemRepository.findAll(filter);
    } catch (RuntimeException e) {
      throw new QueryExecutionException(QUERY_EXECUTION_EXCEPTION, e.getCause());
    }

    return dictionaryItems.stream()
        .map(DictionaryItem::getItem)
        .collect(Collectors.toList());
  }

  @Override
  public Page<JsonNode> query(Pageable pageable, String dictionary, String locale, String queryParams) {
    List<JsonNode> result = query(dictionary, locale, queryParams);
    return new PageImpl<>(result, pageable, result.size());
  }

  @Override
  public Page<JsonNode> getDictionaryContent(Pageable pageable, String dictionary, String locale) {
    List<JsonNode> result = getDictionaryContent(dictionary, locale);
    return new PageImpl<>(result, pageable, result.size());
  }

  @Override
  public List<JsonNode> getDictionaryContent(String dictionary, String locale) {
    return dictionaryItemRepository.findAllByDictionaryNameAndLocaleAndArchiveIsFalse(dictionary, locale).stream()
        .map(DictionaryItem::getItem)
        .collect(Collectors.toList());
  }

  @Override
  public JsonNode getRow(String dictionary, String locale, Long id) {
    return dictionaryItemRepository.findByDictionaryNameAndLocaleAndIdAndArchiveIsFalse(dictionary, locale, id)
        .map(DictionaryItem::getItem)
        .orElseThrow(() -> new DataNotFoundException(DICTIONARY_ENTRY_NOT_FOUND));
  }

  @Transactional
  @Override
  public JsonNode updateDictionaryContent(JsonNode updateDto, String dictionaryName, String locale, Long id) {
    Dictionary dictionary = getDictionaryByName(dictionaryName);
    validateItem(dictionary, updateDto);

    DictionaryItem oldItem = dictionaryItemRepository.findByDictionaryNameAndLocaleAndIdAndArchiveIsFalse(
        dictionary.getName(), locale, id)
        .orElseThrow(() -> new DataNotFoundException(DICTIONARY_ENTRY_NOT_FOUND));

    oldItem.setItem(updateDto);
    dictionaryItemRepository.save(oldItem);

    return oldItem.getItem();
  }

  @Transactional
  @Override
  public JsonNode fillDictionaryContent(JsonNode insertData, String dictionaryName, String locale) {
    Dictionary dictionary = getDictionaryByName(dictionaryName);
    validateItem(dictionary, insertData);

    DictionaryItem newItem = DictionaryItem.builder()
        .dictionaryName(dictionary.getName())
        .locale(locale)
        .dictionaryId(dictionary.getId())
        .item(insertData)
        .archive(Boolean.FALSE)
        .build();

    dictionaryItemRepository.save(newItem);

    return newItem.getItem();
  }

  @Transactional
  @Override
  public void deleteFromDictionary(String dictionary, String locale, Long id) {
    DictionaryItem item = dictionaryItemRepository.findByDictionaryNameAndLocaleAndIdAndArchiveIsFalse(
        dictionary, locale, id)
         .orElseThrow(() -> new DataNotFoundException(DICTIONARY_ENTRY_NOT_FOUND));
    item.setArchive(Boolean.TRUE);
    dictionaryItemRepository.save(item);
  }

  @SneakyThrows
  private void validateItem(Dictionary dictionary, JsonNode newItem) {
    JsonSchema schema = factory.getJsonSchema(dictionary.getSchema());

    processResponse(dictionary.getName(), schema.validateUnchecked(newItem));
  }

  private void processResponse(String dictionaryName,
      ProcessingReport result) {
    List<ErrorResponse.ErrorDetailResponse> detailResponses = new ArrayList<>();
    result.iterator().forEachRemaining(message ->
        detailResponses.add(new ErrorResponse.ErrorDetailResponse(
            dictionaryName, message.getLogLevel().name(), new String[]{message.getMessage()})));

    if (result.isSuccess()) {
      if (!CollectionUtils.isEmpty(detailResponses)) {
        log.warn(detailResponses.toString());
      }
    } else {
      throw new ValidationException(VALIDATION_EXCEPTION, detailResponses);
    }
  }

  private Dictionary getDictionaryByName(String dictionaryName) {
    return dictionaryRepository.findByName(dictionaryName)
        .orElseThrow(() -> new DataNotFoundException(DICTIONARY_SCHEMA_NOT_FOUND));
  }

}
