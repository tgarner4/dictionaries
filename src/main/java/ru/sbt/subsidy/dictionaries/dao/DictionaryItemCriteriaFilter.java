package ru.sbt.subsidy.dictionaries.dao;

import static ru.sbt.subsidy.dictionaries.services.impl.RsqlParseServiceImpl.UNKNOWN_OPERATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import ru.sbt.subsidy.dictionaries.exceptions.ValidationException;
import ru.sbt.subsidy.dictionaries.util.CriteriaPredicate;

@Slf4j
@UtilityClass
public class DictionaryItemCriteriaFilter {

  private static final String ITEM_COLUMN = "item";
  private static final String DICTIONARY_NAME_COLUMN = "dictionaryName";
  private static final String DICTIONARY_LOCALE_COLUMN = "locale";
  private static final String DICTIONARY_ARCHIVE_COLUMN = "archive";
  private static final String JSONB_EXTRACT_PATH_TEXT = "jsonb_extract_path_text";

  public static Specification<DictionaryItem> getNaiveDictItemSpec(String dictionaryName,
      Map<String, String> params) {
    return (root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();
      predicates.add(builder.equal(root.get(DICTIONARY_NAME_COLUMN), dictionaryName));
      params.forEach((key, value) -> predicates.add(builder.equal(
          builder.function(JSONB_EXTRACT_PATH_TEXT, String.class, root.get(ITEM_COLUMN), builder.literal(key)),
          value
      )));
      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public static Specification<DictionaryItem> getComplexDictItemSpec(String dictionaryName,
      String locale, List<CriteriaPredicate> params) {
    return (root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();
      predicates.add(builder.equal(root.get(DICTIONARY_NAME_COLUMN), dictionaryName));
      predicates.add(builder.equal(root.get(DICTIONARY_ARCHIVE_COLUMN), Boolean.FALSE));
      if (locale != null) {
        predicates.add(builder.equal(root.get(DICTIONARY_LOCALE_COLUMN), locale));
      }
      params.forEach(operand -> {
        switch (operand.getOperator()) {
          case EQUAL:
            predicates.add(
                builder.equal(
                    builder.function(JSONB_EXTRACT_PATH_TEXT, String.class, root.get(ITEM_COLUMN),
                        builder.literal(operand.getKey())),
                    operand.getValue())
            );
            break;
          case LIKE:
            predicates.add(
                builder.like(
                    builder.function(JSONB_EXTRACT_PATH_TEXT, String.class, root.get(ITEM_COLUMN),
                        builder.literal(operand.getKey())),
                    replaceStarWithPercent(operand.getValue()))
            );
            break;
          case GREATER_THAN:
            predicates.add(
                builder.gt(
                    builder.function("to_number", Long.class,
                      builder.function(JSONB_EXTRACT_PATH_TEXT, String.class, root.get(ITEM_COLUMN),
                          builder.literal(operand.getKey())), builder.literal("999999999999.99")),
                    Long.valueOf(operand.getValue()))
            );
            break;
          case LESS_THAN:
            predicates.add(
                builder.lt(
                    builder.function("to_number", Long.class,
                      builder.function(JSONB_EXTRACT_PATH_TEXT, String.class, root.get(ITEM_COLUMN),
                          builder.literal(operand.getKey())), builder.literal("999999999999.99")),
                    Long.valueOf(operand.getValue()))
            );
            break;
          default:
            throw new ValidationException(UNKNOWN_OPERATOR);
        }
      });

      log.debug(predicates.toString());
      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private static String replaceStarWithPercent(String str) {
    return str.replace('*', '%');
  }

}
