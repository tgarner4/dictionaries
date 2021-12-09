package ru.sbt.subsidy.backend

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import ru.sbt.subsidy.dictionaries.dao.DictionaryItem
import ru.sbt.subsidy.dictionaries.dao.DictionaryItemRepository
import spock.lang.Unroll


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
class DictionaryItemApplicationIntegrationIT extends AbstractIntegrationIT {

    private static String VALIDATION_EXCEPTION = "validation.exception"

    @Autowired
    MockMvc mockMvc;
    @Autowired
    DictionaryItemRepository dictionaryItemRepository;

    ObjectMapper mapper = new ObjectMapper()

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get by Id"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/$dictionary/$id"))

        then:
        result.andExpect(status().isOk())

        where:
        dictionary  | id
        "fish_rate" | 1L
        "fish_rate" | 2L
    }

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get by Id - archive=true or not found, exception thrown"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/$dictionary/$id"))

        then:
        result.andExpect(status().is4xxClientError())
        result.andExpect(jsonPath('$.message').value(msg))

        where:
        dictionary  | id | msg
        "fish_rate" | 3L | "dictionary.entry.not.found"
        "fish_rate" | 100L | "dictionary.entry.not.found"

    }

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get all"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/$dictionary"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.content.length()').value(count))

        where:
        dictionary  | pageable | count
        "fish_rate" | PageRequest.unpaged() | 4

    }

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get by query - two IN filters"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/query/$dictionary?query=$query"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.[0].rate').value(rate))

        where:
        dictionary  | query | rate
        "fish_rate" | "productCode==STURGEON,archive==false" | 20
        "fish_rate" | "productCode==SALMON,archive==false" | 2

    }

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get by query - one IN, one LIKE filers"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/query/$dictionary?query=$query"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.[0].rate').value(rate))

        where:
        dictionary  | query | rate
        "fish_rate" | "productCode=like=STUR*,archive==false" | 20
        "fish_rate" | "productCode=like=SALM*,archive==false" | 2
    }

    @Unroll
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller get by query - with empty queryparams"() {
        when:
        def result = mockMvc.perform(get("/v1/dictionaries/query/$dictionary?query=$query"))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.length()').value(count))

        where:
        dictionary  | query | count
        "fish_rate" | ""    | 4
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller update entity"() {
        given:
        def id = 1
        def dictionary = "fish_rate"
        String updateStr = "{\"rate\": 1, \"maxSize\": 2}";
        def updateNode = mapper.readTree(updateStr);

        when:
        def result = mockMvc.perform(put("/v1/dictionaries/$dictionary/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateNode.toString()))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.rate').value(1))
        result.andExpect(jsonPath('$.maxSize').value(2))
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller update entity - with non-existant entity, exception thrown"() {
        given:
        def id = 100L
        def dictionary = "fish_rate"
        String updateStr = "{\"rate\": 1, \"maxSize\": 2}";
        def updateNode = mapper.readTree(updateStr);

        when:
        def result = mockMvc.perform(put("/v1/dictionaries/$dictionary/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateNode.toString()))

        then:
        result.andExpect(status().is4xxClientError())
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller update entity - failed with schema"() {
        given:
        def id = 1
        def dictionary = "fish_rate"
        String updateStr = "{\"rate\": 101, \"maxSize\": 2}";
        def updateNode = mapper.readTree(updateStr);

        when:
        def result = mockMvc.perform(put("/v1/dictionaries/$dictionary/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateNode.toString()))

        then:
        result.andExpect(status().is4xxClientError())
        result.andReturn().response.contentAsString.contains(VALIDATION_EXCEPTION)
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller insert entity"() {
        given:
        def dictionary = "fish_rate"
        String updateStr = "{\"rate\": 99, \"maxSize\": 777}";
        def insertNode = mapper.readTree(updateStr);

        when:
        def result = mockMvc.perform(post("/v1/dictionaries/$dictionary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(insertNode.toString()))

        then:
        result.andExpect(status().isOk())
        result.andExpect(jsonPath('$.rate').value(99))
        result.andExpect(jsonPath('$.maxSize').value(777))
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller insert entity - failed with schema"() {
        given:
        def dictionary = "fish_rate"
        String updateStr = "{\"rate\": 101, \"maxSize\": 2}";
        def insertNode = mapper.readTree(updateStr);

        when:
        def result = mockMvc.perform(post("/v1/dictionaries/$dictionary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(insertNode.toString()))

        then:
        result.andExpect(status().is4xxClientError())
        result.andReturn().response.contentAsString.contains(VALIDATION_EXCEPTION)
    }

    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/init.sql')
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            scripts = 'classpath:test/dictionaries/cleanup.sql')
    def "test controller delete entity"() {
        given:
        def id = 1L
        def dictionaryName = "fish_rate"
        String updateStr = "{\"rate\": 101, \"maxSize\": 2}";
        def insertNode = mapper.readTree(updateStr);

        when:
        mockMvc.perform(delete("/v1/dictionaries/$dictionaryName/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(insertNode.toString()))
        DictionaryItem row = dictionaryItemRepository.findByDictionaryNameAndLocaleAndIdAndArchiveIsTrue(
                dictionaryName,null, id)
                .orElse(null)

        then:
        row.archive
    }


}

