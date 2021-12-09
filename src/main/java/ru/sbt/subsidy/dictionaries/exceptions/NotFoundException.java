package ru.sbt.subsidy.dictionaries.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("squid:S1171")
public class NotFoundException extends SubsidyException {

  {
    this.httpStatus = HttpStatus.NOT_FOUND;
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String messageCode, Object... messageArgs) {
    super(messageCode, messageArgs);
  }

  public NotFoundException(String message, String uuid) {
    super(message, uuid);
  }

  public NotFoundException(Throwable cause) {
    super(cause);
  }

}
