package ru.sbt.subsidy.dictionaries.exceptions;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.sbt.subsidy.dictionaries.api.ErrorResponse;

public class ValidationException extends SubsidyException {

  @Getter
  private List<ErrorResponse.ErrorDetailResponse> detailResponses = new ArrayList<>();

  {
    this.httpStatus = HttpStatus.BAD_REQUEST;
  }

  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(
      String message,
      List<ErrorResponse.ErrorDetailResponse> detailResponses,
      Throwable cause
  ) {
    super(message, cause);
    this.detailResponses = detailResponses;
  }

  public ValidationException(String messageCode, Object... messageArgs) {
    super(messageCode, messageArgs);
  }

  public ValidationException(
      String messageCode,
      List<ErrorResponse.ErrorDetailResponse> detailResponses,
      Object... messageArgs) {
    super(messageCode, messageArgs);
    this.detailResponses = detailResponses;
  }

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(Throwable cause) {
    super(cause);
  }
}
