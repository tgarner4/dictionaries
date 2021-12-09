package ru.sbt.subsidy.dictionaries.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InnerDictionariesController {

  @ApiOperation(value = "Получение содержимого справочника", response = Page.class)
  Page<JsonNode> getDictionaryContent(Pageable pageable, String dictionary, String locale);

  @ApiOperation(value = "Получение содержимого справочника", response = List.class)
  List<JsonNode> getDictionaryContent(String dictionary, String locale);

  @ApiOperation(value = "Получение записи справочника", response = JsonNode.class)
  JsonNode getRow(String dictionary, String locale, Long id);

  @ApiOperation(value = "Редактирование содержимого справочника", response = JsonNode.class)
  JsonNode updateDictionary(JsonNode body, String dictionary, String locale, Long id);

  @ApiOperation(value = "Дополнение содержимого справочника", response = JsonNode.class)
  JsonNode fillDictionary(JsonNode body, String dictionary, String locale);

  @ApiOperation(value = "Удаление содержимого справочника")
  void deleteFromDictionary(String dictionary, String locale, Long id);

  @ApiOperation(value = "Запрос к справочнику")
  List<JsonNode> query(String dictionary, String locale, String queryParams);

  @ApiOperation(value = "Запрос к справочнику")
  Page<JsonNode> query(Pageable pageable, String dictionary, String locale, String queryParams);

  @ApiOperation(value = "Старт онлайн миграции для словаря")
  String startMigration(String dictionaryName);

}
