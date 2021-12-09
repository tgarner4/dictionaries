package ru.sbt.subsidy.dictionaries.exceptions;

import java.util.UUID;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SubsidyException extends RuntimeException {

  protected String uuid = UUID.randomUUID().toString();
  protected HttpStatus httpStatus;
  protected Object[] messageArgs = new Object[]{};

  public SubsidyException(String messageCode, Throwable cause) {
    super(messageCode, cause);
  }

  public SubsidyException(String messageCode) {
    super(messageCode);
  }

  public SubsidyException(String messageCode, Object... messageArgs) {
    super(messageCode);
    this.messageArgs = messageArgs;
  }

  public SubsidyException(String messageCode, Throwable cause, Object... messageArgs) {
    super(messageCode, cause);
    this.messageArgs = messageArgs;
  }

  public SubsidyException(String message, String uuid) {
    super(message);
    this.uuid = uuid;
  }

  public SubsidyException(Throwable cause) {
    super(cause);
  }
}
