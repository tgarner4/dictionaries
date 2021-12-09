package ru.sbt.subsidy.dictionaries.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dictionaries_items")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DictionaryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dictionaries_items_generator")
  @SequenceGenerator(name = "dictionaries_items_generator", sequenceName = "dictionaries_items_id_seq", allocationSize = 1)
  private Long id;

  private Long dictionaryId;

  private String dictionaryName;

  private Boolean archive;

  private String locale;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private JsonNode item;

}
