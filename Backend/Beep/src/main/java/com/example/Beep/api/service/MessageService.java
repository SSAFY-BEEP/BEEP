package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MessageService {

    void saveMessage(MessageRequestDto.persistMessage persistMessage);

    void deleteMessage(MessageRequestDto.persistMessage persistMessage);

    List<MessageResponseDto> findMessage(MessageRequestDto.persistMessage persistMessage);

    List<MessageResponseDto> openMessage(MessageRequestDto.persistMessage persistMessage);
}
