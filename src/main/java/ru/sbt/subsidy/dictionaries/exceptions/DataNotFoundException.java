package ru.sbt.subsidy.dictionaries.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends SubsidyException {

  {
    super.httpStatus = HttpStatus.NOT_FOUND;
  }

  public DataNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException(String messageCode, Object... messageArgs) {
    super(messageCode, messageArgs);
  }

  public DataNotFoundException(Throwable cause) {
    super(cause);
  }

}
