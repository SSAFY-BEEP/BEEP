package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresetRepository extends JpaRepository<Preset,Long> {

    @Query(value = "select * from preset p where p.number = :uNumber And p.user_id =:pid",nativeQuery = true)
    Optional<Preset>findPreset(@Param("uNumber") Long uNumber,@Param("pid") Long pid);
}
