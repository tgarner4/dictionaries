package ru.sbt.subsidy.dictionaries.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ComparisonOperator {
  EQUAL("=="),  LIKE("=like="), GREATER_THAN("=gt="), LESS_THAN("=lt=");

  private final String operator;
}
