package ru.sbt.subsidy.dictionaries.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long>,
    JpaSpecificationExecutor<DictionaryItem> {

  Optional<DictionaryItem> findByDictionaryNameAndLocaleAndIdAndArchiveIsFalse(String dictionaryName, String locale, Long id);

  Optional<DictionaryItem> findByDictionaryNameAndLocaleAndIdAndArchiveIsTrue(String dictionaryName, String locale, Long id);

  List<DictionaryItem> findAllByDictionaryNameAndLocaleAndArchiveIsFalse(String dictionaryName, String locale);
}
