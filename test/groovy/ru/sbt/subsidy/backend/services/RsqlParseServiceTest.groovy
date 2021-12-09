package ru.sbt.subsidy.backend.services

import ru.sbt.subsidy.dictionaries.exceptions.ValidationException
import ru.sbt.subsidy.dictionaries.services.ComparisonOperator
import ru.sbt.subsidy.dictionaries.services.impl.RsqlParseServiceImpl
import ru.sbt.subsidy.dictionaries.util.CriteriaPredicate
import spock.lang.Specification

class RsqlParseServiceTest extends Specification {

    RsqlParseServiceImpl rsqlParseService = new RsqlParseServiceImpl()

    def "test parse1"() {
        given:
        def query = "inn==1,kpp==2"
        def entry1 = CriteriaPredicate.builder().operator(ComparisonOperator.EQUAL).key("inn").value("1").build();
        def entry2 = CriteriaPredicate.builder().operator(ComparisonOperator.EQUAL).key("kpp").value("2").build();

        when:
        def result = rsqlParseService.parse(query, ",")

        then:
        result.size() == 2
        result.contains(entry1)
        result.contains(entry2)
    }

    def "test parse2"() {
        given:
        def query = "inn==1,kpp=like=2*,field1=gt=5,field2=lt=10"
        def entry1 = CriteriaPredicate.builder().operator(ComparisonOperator.EQUAL).key("inn").value("1").build();
        def entry2 = CriteriaPredicate.builder().operator(ComparisonOperator.LIKE).key("kpp").value("2*").build();
        def entry3 = CriteriaPredicate.builder().operator(ComparisonOperator.GREATER_THAN).key("field1").value("5").build();
        def entry4 = CriteriaPredicate.builder().operator(ComparisonOperator.LESS_THAN).key("field2").value("10").build();

        when:
        def result = rsqlParseService.parse(query, ",")

        then:
        result.size() == 4
        result.contains(entry1)
        result.contains(entry2)
        result.contains(entry3)
        result.contains(entry4)
    }

    def "test parse throws ValidationException"() {
        given:
        def query = "inn==1,kpp=like1=2*"

        when:
        rsqlParseService.parse(query, ",")

        then:
        thrown(ValidationException)
    }

    def "test parse throws IllegalArgumentException"() {
        given:
        def query = "inn==,kpp==2*"

        when:
        rsqlParseService.parse(query, ",")

        then:
        thrown(IllegalArgumentException)
    }
}
