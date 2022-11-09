package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block,Long> {

//    @Query(value = "select * from Block b where b.user_id = :pNum and b.target_id = :bNum",nativeQuery = true)
//    Block findDelete(@Param("pNum") Long pNum,@Param("bNum") Long bNum);
    //차단여부 확인
    boolean existsByUserAndTarget(User user, User target);

    //메세지id로 차단관계 삭제
    void deleteByMessage(Message message);
}
