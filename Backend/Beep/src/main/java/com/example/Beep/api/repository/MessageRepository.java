package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query(value = "select * from message m where m.sender_id = :userId and m.owner_id = :userId",nativeQuery = true)
    List<Message> findSendMessage(Long userId);

    @Query(value = "select * from message m where m.receiver_id = :userId and m.owner_id = :userId",nativeQuery = true)
    List<Message> findReceiveMessage(Long userId);

    @Query(value = "select * from message m where m.receiver_id = :userId and m.owner_id = :userId and type = :type",nativeQuery = true)
    List<Message> findReceiveMessageByType(Long userId, Integer type);

    List<Message> findByOwnerId(Long ownerId);
    List<Message> findBySenderIdAndType(Long senderId, Integer type);

}
