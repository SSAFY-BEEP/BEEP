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

    @Query(value = "select * from Message m where m.sender_id = :userId and m.owner_id = :userId",nativeQuery = true)
    List<Message> findSendMessage(Long userId);

    @Query(value = "select * from Message m where m.receiver_id = :userId and m.owner_id = :userId",nativeQuery = true)
    List<Message> findReceiveMessage(Long userId);

    @Query(value = "select * from Message m where m.receiver_id = :userId and m.owner_id = :userId and type = :type",nativeQuery = true)
    List<Message> findReceiveMessageByType(Long userId, Integer type);

//    @Query(value = "select * from Message m where m.sender_id = :sid and m.receiver_id = :rid",nativeQuery = true)
//    List<Message>messageList(@Param("sid") Long sid, @Param("rid") Long rid);
}
