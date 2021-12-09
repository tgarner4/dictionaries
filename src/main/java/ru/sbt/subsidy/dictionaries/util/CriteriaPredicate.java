package ru.sbt.subsidy.dictionaries.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sbt.subsidy.dictionaries.services.ComparisonOperator;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaPredicate {
  private String key;
  private String value;
  private ComparisonOperator operator;
}
