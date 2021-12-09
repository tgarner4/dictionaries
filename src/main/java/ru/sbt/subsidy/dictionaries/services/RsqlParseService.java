package ru.sbt.subsidy.dictionaries.services;

import java.util.List;
import ru.sbt.subsidy.dictionaries.util.CriteriaPredicate;

public interface RsqlParseService {

  List<CriteriaPredicate> parse(String query, String pairsDelimiter);
}
