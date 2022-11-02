package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import java.util.List;

public interface Message24Service {
    //받은 메세지 조회
    List<Message24> getReceiveMessage(Long receiver);

    //보낸 메세지 조회
    List<Message24> getSendMessage(Long sender);

    //해당 메세지 조회

    //메세지 발송(저장)
    void sendMessage(Message24RequestDto.sendMessage message);

    //메세지 보관
    void saveMessage(String id);

    //메세지 차단


   //모든 메세지 조회
    List<Message24> getAllMessage();

    Message24 getMessage(String id);

    void deleteMessageById(String id);
}
