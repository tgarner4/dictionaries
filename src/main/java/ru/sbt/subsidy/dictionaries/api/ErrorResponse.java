package ru.sbt.subsidy.dictionaries.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.sbt.subsidy.dictionaries.infra.CustomLocalDateTimeSerializer;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private String id;
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  private LocalDateTime timestamp;
  private String message;
  private List<ErrorDetailResponse> errors;

  public ErrorResponse(String id, LocalDateTime timestamp, String message) {
    this.id = id;
    this.timestamp = timestamp;
    this.message = message;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ErrorDetailResponse {

    private String field;
    private String message;
    @JsonIgnore
    private Object[] args;

    public ErrorDetailResponse(String field, String message) {
      this.field = field;
      this.message = message;
    }
  }

}
