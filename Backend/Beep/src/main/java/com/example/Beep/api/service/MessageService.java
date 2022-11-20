package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageService {

//    void saveSendMessage(MessageRequestDto.persistMessage persistMessage);
//    void saveReceiveMessage(MessageRequestDto.persistMessage persistMessage);

    //전체 조회
    List<MessageResponseDto> getAll();

    //메세지 삭제
    void deleteMessage(Long id);

    void cancelSave(Long id);

    //보낸메세지 조회
    List<MessageResponseDto> findSendMessage();
    //보관메세지 조회(보관1, 차단2)
    List<MessageResponseDto> findReceiveMessageByType(Integer type);

    //메세지 타입 변경(보관1, 차단2)
    String changeMessageType(Long messageId,Integer type);

    //메세지 태그 수정
    void updateTag(MessageRequestDto.updateTag updateTag);
}
