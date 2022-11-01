package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select * from Message m where m.sender_id = :sid and m.receiver_id = :rid and m.owner_id = :sid",nativeQuery = true)
    Message findMessageSend(@Param("sid") Long sid, @Param("rid") Long rid);

    @Query(value = "select * from Message m where m.sender_id = :sid and m.receiver_id = :rid and m.owner_id = :rid",nativeQuery = true)
    Message findMessageReceive(@Param("sid") Long sid, @Param("rid") Long rid);

    @Query(value = "select * from Message m where DATEDIFF(Hour,m.time, =: time)>=24 ",nativeQuery = true)
    List<Message> openMessage(@Param("time") LocalDateTime time);

    @Query(value = "select * from Message m where m.sender_id = :sid",nativeQuery = true)
    List<Message>messageList(@Param("sid") Long sid);
}
