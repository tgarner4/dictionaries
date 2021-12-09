package ru.sbt.subsidy.dictionaries.controllers;


import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sbt.subsidy.dictionaries.api.ErrorResponse;
import ru.sbt.subsidy.dictionaries.api.ErrorResponse.ErrorDetailResponse;
import ru.sbt.subsidy.dictionaries.exceptions.NotFoundException;
import ru.sbt.subsidy.dictionaries.exceptions.SubsidyException;
import ru.sbt.subsidy.dictionaries.exceptions.ValidationException;
import ru.sbt.subsidy.dictionaries.services.MessageService;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

  private final MessageService messageService;

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(final ValidationException ex) {
    String message = messageService.getMessage(ex.getMessage(), ex.getMessageArgs());
    List<ErrorDetailResponse> detailResponses = ex.getDetailResponses().stream()
        .peek(dr -> {
          String msg = dr.getMessage();
          Object[] args = dr.getArgs();
          msg = messageService.getMessage(msg, args);
          dr.setMessage(msg);
        }).collect(Collectors.toList());
    ErrorResponse errorResponse = new ErrorResponse(ex.getUuid(), LocalDateTime.now(),
        message, detailResponses);

    return ResponseEntity
        .status(ex.getHttpStatus())
        .body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
    String uuid = UUID.randomUUID().toString();
    BindingResult bindingResult = e.getBindingResult();
    String bindingMessage = messageService.getMessage("exception.binding.message");

    List<ErrorResponse.ErrorDetailResponse> errors = bindingResult.getFieldErrors().stream()
        .map(o -> new ErrorDetailResponse(o.getField(), o.getDefaultMessage()))
        .collect(Collectors.toList());

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(uuid, LocalDateTime.now(), bindingMessage, errors));
  }

  @ExceptionHandler(SubsidyException.class)
  public ResponseEntity<ErrorResponse> handleCocosError(final SubsidyException ex) {
    String message = messageService.getMessage(ex.getMessage(), ex.getMessageArgs());
    if (!(ex instanceof NotFoundException)) {
      logError(ex.getUuid(), message, ex);
    }
    ErrorResponse errorResponse = new ErrorResponse(ex.getUuid(), LocalDateTime.now(), message);
    HttpStatus httpStatus = ex.getHttpStatus() != null
        ? ex.getHttpStatus()
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return ResponseEntity
        .status(httpStatus)
        .body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedError(final Exception ex) {
    String uuid = UUID.randomUUID().toString();
    String message = StringUtils.isEmpty(ex.getMessage())
        ? ex.getClass().getName() + " at " + ex.getStackTrace()[0].toString()
        : messageService.getMessage(ex.getMessage());
    logError(uuid, message, ex);
    ErrorResponse errorResponse = new ErrorResponse(uuid, LocalDateTime.now(), message);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(errorResponse);
  }

  private void logError(String uuid, String message, Throwable throwable) {
    log.error(
        "Error {} {} {} {} ",
        keyValue("uuid", uuid),
        keyValue("messages", message),
        keyValue("success", false),
        keyValue("result", throwable),
        throwable
    );
  }
}
