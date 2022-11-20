package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {

    List<Dictionary> findByWordContaining(String word);
    List<Dictionary> findByValueContaining(String value);

    @Query(value = "select * from dictionary order by rand() limit 5",nativeQuery = true)
    List<Dictionary>findRandom();

}
