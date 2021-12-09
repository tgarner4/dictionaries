package ru.sbt.subsidy.dictionaries.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");

  public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
    super(t);
  }

  public CustomLocalDateTimeSerializer() {
    this(null);
  }

  @Override
  public void serialize(
      LocalDateTime value,
      JsonGenerator gen,
      SerializerProvider arg2)
      throws IOException {

    gen.writeString(FORMATTER.format(value));
  }
}
