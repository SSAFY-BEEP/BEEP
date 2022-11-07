package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.dto.S3RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import java.util.List;

public interface Message24Service {
    //받은 메세지 조회
    List<Message24> getReceiveMessage();

    //보낸 메세지 조회
    List<Message24> getSendMessage();

//   void sendMessage(Message24RequestDto.sendMessage message, boolean isBlocked);

    //메세지 발송(저장)
    void sendMessageWithFile(S3RequestDto.sendMessage24 message, boolean isBlocked);

    //메세지 보관or차단 등 타입 바꾸고 DB에 저장
    Long changeMessageType(String messageId, Integer type);

    //모든 메세지 조회
    List<Message24> getAllMessage();

    //해당 메세지 조회
    Message24 getMessage(String id);

    //해당 메세지 삭제
    void deleteMessageById(String id);
}
