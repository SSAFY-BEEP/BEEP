package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface MessageService {

    void saveMessage(MessageRequestDto.sendMessage sendMessage);

    void deleteMessage(MessageRequestDto.sendMessage sendMessage);
}
