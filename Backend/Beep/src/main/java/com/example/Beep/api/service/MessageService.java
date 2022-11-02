package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageService {

    void saveSendMessage(MessageRequestDto.persistMessage persistMessage);
    void saveReceiveMessage(MessageRequestDto.persistMessage persistMessage);

    void deleteMessage(Long id);

    List<MessageResponseDto> findSendMessage(Long id);
    List<MessageResponseDto> findReceiveMessage(Long id);

    void updateTag(MessageRequestDto.updateTag updateTag);
}
