package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Relation24RequestDto {
    private Long receiverId;
    private Long senderId;
}
