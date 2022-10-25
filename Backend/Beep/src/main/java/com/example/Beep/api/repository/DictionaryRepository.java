package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {
}
