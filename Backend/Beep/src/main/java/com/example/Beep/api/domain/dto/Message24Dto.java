package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class Message24Dto {

    @Getter
    @Builder
    public static class sendMessage {
        private String audioUri;
        private String content;
        private Long receiver;
        private Long sender;
    }
}
