package ru.sbt.subsidy.dictionaries.services.impl;

import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sbt.subsidy.dictionaries.exceptions.ValidationException;
import ru.sbt.subsidy.dictionaries.services.ComparisonOperator;
import ru.sbt.subsidy.dictionaries.services.RsqlParseService;
import ru.sbt.subsidy.dictionaries.util.CriteriaPredicate;

@Service
@Slf4j
public class RsqlParseServiceImpl implements RsqlParseService {

  public static final String UNKNOWN_OPERATOR = "unknown.comparison.operator";

  private static final String ILLEGAL_INPUT_EXCEPTION = "illegal.input.arguments";
  private static final String NULL_POINTER_EXCEPTION = "null.pointer.exception.in.predicate";

  @Override
  public List<CriteriaPredicate> parse(String query, String pairsDelimiter) {
    Iterable<String> pairs = Splitter.on(pairsDelimiter).split(query);
    List<CriteriaPredicate> result = new ArrayList<>();

    pairs.iterator().forEachRemaining(pair -> parseToken(pair, result));

    log.debug(result.toString());
    return Collections.unmodifiableList(result);
  }

  private void parseToken(String pair, List<CriteriaPredicate> resultList) {
    if (pair.contains(ComparisonOperator.EQUAL.getOperator())) {
      validateAndSplitPair(pair, ComparisonOperator.EQUAL, resultList);
    } else if (pair.contains(ComparisonOperator.LIKE.getOperator())) {
      validateAndSplitPair(pair, ComparisonOperator.LIKE, resultList);
    } else if (pair.contains(ComparisonOperator.GREATER_THAN.getOperator())) {
      validateAndSplitPair(pair, ComparisonOperator.GREATER_THAN, resultList);
    } else if (pair.contains(ComparisonOperator.LESS_THAN.getOperator())) {
      validateAndSplitPair(pair, ComparisonOperator.LESS_THAN, resultList);
    } else {
      throw new ValidationException(UNKNOWN_OPERATOR);
    }
  }

  private void validateAndSplitPair(String pair, ComparisonOperator operator, List<CriteriaPredicate> resultList) {
    String[] splitedPair = pair.split(operator.getOperator());
    validatePairNotNull(splitedPair);
    resultList.add(CriteriaPredicate.builder()
        .operator(operator)
        .key(splitedPair[0])
        .value(splitedPair[1])
        .build());
  }

  private void validatePairNotNull(String[] pair) {
    if (pair.length != 2) {
      throw new IllegalArgumentException(ILLEGAL_INPUT_EXCEPTION);
    }
    Objects.requireNonNull(pair[0], NULL_POINTER_EXCEPTION);
    Objects.requireNonNull(pair[1], NULL_POINTER_EXCEPTION);
  }
}
