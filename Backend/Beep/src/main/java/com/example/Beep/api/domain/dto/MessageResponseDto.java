package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MessageResponseDto {

    private Long id;
    private String audioUri;
    private String content;
    private String receiverPhoneNumber;
    private String senderPhoneNumber;
    private String ownerPhoneNumber;
    private LocalDateTime localDateTime;
    private String tag;
    private Integer type;
}
