package ru.sbt.subsidy.dictionaries.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Sort;

@JsonComponent
@Slf4j
public class SortJsonSerializer extends JsonSerializer<Sort> {

  @Override
  public void serialize(Sort value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartArray();

    value.iterator().forEachRemaining(v -> {
      try {
        gen.writeObject(v);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    });

    gen.writeEndArray();
  }

  @Override
  public Class<Sort> handledType() {
    return Sort.class;
  }
}
