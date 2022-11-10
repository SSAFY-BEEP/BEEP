package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresetRepository extends JpaRepository<Preset,Long> {

    @Query(value = "select * from preset p where p.number = :number and p.user_id =:userId and p.part = :part",nativeQuery = true)
    Optional<Preset>findPreset(Long number, Long userId, Integer part);
}
