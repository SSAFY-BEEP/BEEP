package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block,Long> {

    @Query(value = "select * from Block b where b.user_id = :pNum and b.target_id = :bNum",nativeQuery = true)
    Block findDelete(@Param("pNum") Long pNum,@Param("bNum") Long bNum);
}
