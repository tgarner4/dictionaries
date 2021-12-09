package ru.sbt.subsidy.dictionaries.exceptions;

public class QueryExecutionException extends SubsidyException {

  public QueryExecutionException(String messageCode, Throwable cause) {
    super(messageCode, cause);
  }

  public QueryExecutionException(String messageCode) {
    super(messageCode);
  }

  public QueryExecutionException(String messageCode, Object... messageArgs) {
    super(messageCode, messageArgs);
  }

  public QueryExecutionException(String messageCode, Throwable cause, Object... messageArgs) {
    super(messageCode, cause, messageArgs);
  }

  public QueryExecutionException(String message, String uuid) {
    super(message, uuid);
  }

  public QueryExecutionException(Throwable cause) {
    super(cause);
  }
}
