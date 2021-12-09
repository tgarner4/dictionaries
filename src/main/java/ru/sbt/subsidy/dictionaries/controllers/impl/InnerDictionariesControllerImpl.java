package ru.sbt.subsidy.dictionaries.controllers.impl;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sbt.subsidy.dictionaries.services.InnerDictionariesService;
import ru.sbt.subsidy.dictionaries.services.MigrationService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/dictionaries")
public class InnerDictionariesControllerImpl implements
    ru.sbt.subsidy.dictionaries.controllers.InnerDictionariesController {

  private final InnerDictionariesService innerDictionariesService;
  private final MigrationService migrationService;

  @Override
  @GetMapping("/{dictionary}")
  public Page<JsonNode> getDictionaryContent(Pageable pageable,
      @PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale) {
    return innerDictionariesService.getDictionaryContent(pageable, dictionary, locale);
  }

  @Override
  @GetMapping("/list/{dictionary}")
  public List<JsonNode> getDictionaryContent(@PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale) {
    return innerDictionariesService.getDictionaryContent(dictionary, locale);
  }

  @Override
  @GetMapping("/{dictionary}/{id}")
  public JsonNode getRow(@PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale,
      @PathVariable Long id) {
    return innerDictionariesService.getRow(dictionary, locale, id);
  }

  @Override
  @PutMapping("/{dictionary}/{id}")
  public JsonNode updateDictionary(@RequestBody JsonNode body,
      @PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale,
      @PathVariable("id") Long id) {
    return innerDictionariesService.updateDictionaryContent(body, dictionary, locale, id);
  }

  @Override
  @PostMapping("/{dictionary}")
  public JsonNode fillDictionary(@RequestBody @Valid JsonNode body,
      @PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale) {
    return innerDictionariesService.fillDictionaryContent(body, dictionary, locale);
  }

  @Override
  @DeleteMapping("/{dictionary}/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFromDictionary(@PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale,
      @PathVariable(value = "id") Long id) {
    innerDictionariesService.deleteFromDictionary(dictionary, locale, id);
  }

  @Override
  @GetMapping("/query/{dictionary}")
  public List<JsonNode> query(@PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale,
      @RequestParam("query") String queryParams) {
    return innerDictionariesService.query(dictionary, locale, queryParams);
  }

  @Override
  @GetMapping("/query_pageable/{dictionary}")
  public Page<JsonNode> query(Pageable pageable, @PathVariable String dictionary,
      @RequestParam(name = "locale", required = false) String locale,
      @RequestParam("query") String queryParams) {
    return innerDictionariesService.query(pageable, dictionary, locale, queryParams);
  }

  @Override
  @GetMapping("/start_migration")
  public String startMigration(String dictionaryName) {
    return migrationService.startMigration(dictionaryName);
  }

}
