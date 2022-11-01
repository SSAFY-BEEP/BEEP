package com.example.Beep.api.domain.dto;

import com.example.Beep.api.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

public class MessageRequestDto {

    @Builder
    @Getter
    public static class persistMessage {
        private String audioUri;
        private String content;
        private String receiverPhoneNumber;
        private String senderPhoneNumber;
        private String ownerPhoneNumber;
        private LocalDateTime localDateTime;
    }
}
