package ru.sbt.subsidy.backend.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jsonschema.main.JsonSchema
import com.github.fge.jsonschema.main.JsonSchemaFactory
import ru.sbt.subsidy.dictionaries.dao.Dictionary
import ru.sbt.subsidy.dictionaries.dao.DictionaryItem
import ru.sbt.subsidy.dictionaries.dao.DictionaryItemRepository
import ru.sbt.subsidy.dictionaries.dao.DictionaryRepository
import ru.sbt.subsidy.dictionaries.services.RsqlParseService
import ru.sbt.subsidy.dictionaries.services.impl.InnerDictionariesServiceImpl
import spock.lang.Specification

class InnerDictionariesServiceTest extends Specification {

    private DictionaryItemRepository dictionaryItemRepository = Mock()
    private DictionaryRepository dictionaryRepository = Mock()
    private JsonSchemaFactory factory = JsonSchemaFactory.byDefault()

    private RsqlParseService rsqlParseService = Mock()

    private ObjectMapper objectMapper = new ObjectMapper();

    def innerDictionariesService = new InnerDictionariesServiceImpl(
            dictionaryItemRepository, dictionaryRepository, factory, rsqlParseService)

    def "test update"() {
        given:
        def updateStr = "{ \"rate\": 1, \"maxSize\": 10}"
        def oldStr = "{ \"rate\": 50, \"maxSize\": 50, \"some_field\": false }"
        def schemaStr = "{}";
        JsonNode updateNode = objectMapper.readTree(updateStr)
        JsonNode oldNode = objectMapper.readTree(oldStr)
        JsonNode schemaNode = objectMapper.readTree(schemaStr)
        JsonSchemaFactory factory2 = JsonSchemaFactory.byDefault()
        JsonSchema jsonSchema = factory2.getJsonSchema(schemaNode)
        def dictName = "test"
        def locale = null
        def id = 1L
        dictionaryRepository.findByName(_) >>
                Optional.of(Dictionary.builder().name(dictName).schema(schemaNode).build())
        dictionaryItemRepository.findByDictionaryNameAndLocaleAndIdAndArchiveIsFalse(_, _, _) >>
                Optional.of(DictionaryItem.builder().dictionaryName(dictName).item(oldNode).build())
        factory.getJsonSchema(_) >> jsonSchema

        when:
        def result = innerDictionariesService.updateDictionaryContent(updateNode, dictName, locale, id)

        then:
        result.size() == 2
        ((ObjectNode)result).get("rate").asLong() == 1L
        ((ObjectNode)result).get("maxSize").asLong() == 10L
    }

}
