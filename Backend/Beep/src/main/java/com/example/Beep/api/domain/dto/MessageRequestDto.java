package com.example.Beep.api.domain.dto;

import com.example.Beep.api.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

public class MessageRequestDto {

    @Builder
    @Getter
    public static class sendMessage {
        private String audioUri;
        private String content;
        private String receiverPhoneNumber;
        private String senderPhoneNumber;
    }
}
