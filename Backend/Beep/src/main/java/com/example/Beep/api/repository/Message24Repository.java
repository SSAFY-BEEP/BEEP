package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message24;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Message24Repository extends CrudRepository<Message24, String> {
    //모든 메세지 조회
    List<Message24> findAll();

    //받은 메세지 목록 조회
    List<Message24> findAllByReceiverNumAndOwnerNum(String receiverNum, String ownerNum);
//    @Query(value = "select * from Relation r where r.receiver_id = :receiver", nativeQuery = true)
//    List<Message24> findReceiveMessage(@Param("receiver") Long receiver, @Param("type") Integer type);


    //보낸 메세지 목록 조회
    List<Message24> findAllBySenderNumAndOwnerNum(String senderNum, String ownerNum);

    //해당 메세지 조회
    Optional<Message24> findById(String id);
    Optional<Message24> findByTime(LocalDateTime time);

    //메세지 삭제
    void deleteById(String id);
}
