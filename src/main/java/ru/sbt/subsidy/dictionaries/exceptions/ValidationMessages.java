package ru.sbt.subsidy.dictionaries.exceptions;

public final class ValidationMessages {

  private ValidationMessages() {

  }

  public static final String FIELD_IS_NOT_EMPTY = "{validation.field.empty}";
  public static final String FIELD_MIN_SIZE = "{validation.field.min} ";
  public static final String FIELD_MAX_SIZE = "{validation.field.max} ";
  public static final String FIELD_POSITIVE = "{validation.field.positive}";
}
