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
@Table(name = "dictionaries")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Dictionary {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dictionaries_generator")
  @SequenceGenerator(name = "dictionaries_generator", sequenceName = "dictionaries_id_seq", allocationSize = 1)
  private Long id;

  private String name;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private JsonNode schema;

}
