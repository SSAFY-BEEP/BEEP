package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select * from Message m where m.sender_id = :sid and m.receiver_id = :rid",nativeQuery = true)
    Message findMessage(@Param("sid") Long sid, @Param("rid") Long rid);
}
