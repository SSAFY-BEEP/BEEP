package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select * from Message m where m.sender_id = :sid and m.owner_id = :sid",nativeQuery = true)
    List<Message> findMessageSend(@Param("sid") Long sid);

    @Query(value = "select * from Message m where m.receiver_id = :rid and m.owner_id = :rid",nativeQuery = true)
    List<Message> findMessageReceive(@Param("rid") Long rid);

//    @Query(value = "select * from Message m where m.sender_id = :sid and m.receiver_id = :rid",nativeQuery = true)
//    List<Message>messageList(@Param("sid") Long sid, @Param("rid") Long rid);
}
