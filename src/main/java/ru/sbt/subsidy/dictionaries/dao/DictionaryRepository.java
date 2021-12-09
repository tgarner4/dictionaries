package ru.sbt.subsidy.dictionaries.dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {

  Optional<Dictionary> findByName(String name);
}
