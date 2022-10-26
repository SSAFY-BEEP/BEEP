package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, String> {
}