package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message24;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface Message24Repository extends CrudRepository<Message24, String> {
    //모든 메세지 조회
    List<Message24> findAll();

    //받은 메세지 목록 조회
    List<Message24> findAllByReceiver(Long receiver);

    //보낸 메세지 목록 조회
    List<Message24> findAllBySender(Long sender);

    //해당 메세지 조회
    Message24 getById(String id);

    Optional<Message24> findById(String id);

    //메세지 삭제
    void deleteById(String id);

    //메세지 차단


}
