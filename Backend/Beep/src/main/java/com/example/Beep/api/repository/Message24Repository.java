package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Message24;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Message24Repository extends CrudRepository<Message24, Long> {
    //받은 메세지 목록 조회
    List<Message24> findAllByReceiver(String receiver);

    //보낸 메세지 목록 조회
    List<Message24> findAllBySender(String sender);

}
