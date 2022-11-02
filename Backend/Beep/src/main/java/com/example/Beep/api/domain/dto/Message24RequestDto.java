package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class Message24RequestDto {
    @Getter
    @Builder
    public static class sendMessage {
        private String audioUri;
        private String content;
        private String receiverNum;
        private String senderNum;
    }

    @Getter
    @Builder
    public static class changeMessage {
        private String id;      //메세지 아이디
        private String ownerNum;   //
    }
}
