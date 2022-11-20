package com.example.Beep.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BlockResponseDto {
    private Long id;
    private Long messageId;
    private Long userId;
    private Long targetId;
}
