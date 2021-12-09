package ru.sbt.subsidy.dictionaries.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.sbt.subsidy.dictionaries.infra.CustomSortDeserializer;

public class RestPageImpl<T> extends PageImpl<T> {

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public RestPageImpl(@JsonProperty("content") List<T> content,
      @JsonProperty("number") int number,
      @JsonProperty("size") int size,
      @JsonProperty("totalElements") Long totalElements,
      @JsonProperty("pageable") JsonNode pageable,
      @JsonProperty("last") boolean last,
      @JsonProperty("totalPages") int totalPages,
      @JsonProperty("sort") Sort sort,
      @JsonProperty("first") boolean first,
      @JsonProperty("numberOfElements") int numberOfElements) {
    super(content, PageRequest.of(number, size, sort), totalElements);
  }

  public RestPageImpl(List<T> content, Pageable pageable, long totalElements) {
    super(content, pageable, totalElements);
  }

  public RestPageImpl(List<T> content) {
    super(content);
  }

  public RestPageImpl() {
    super(new ArrayList<>());
  }

  @JsonDeserialize(using = CustomSortDeserializer.class)
  public void setSort(Sort sort) {
    super.getSort().and(sort);
  }
}
