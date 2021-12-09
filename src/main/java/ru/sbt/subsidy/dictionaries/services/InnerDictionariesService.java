package ru.sbt.subsidy.dictionaries.services;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InnerDictionariesService {

  Page<JsonNode> getDictionaryContent(Pageable pageable, String dictionary, String locale);

  List<JsonNode> getDictionaryContent(String dictionary, String locale);

  JsonNode getRow(String dictionary, String locale, Long id);

  JsonNode updateDictionaryContent(JsonNode updateDto, String dictionary, String locale, Long id);

  JsonNode fillDictionaryContent(JsonNode updateDto, String dictionary, String locale);

  void deleteFromDictionary(String dictionary, String locale, Long id);

  List<JsonNode> query(String dictionary, String locale, String queryParams);

  Page<JsonNode> query(Pageable pageable, String dictionary, String locale, String queryParams);

}
