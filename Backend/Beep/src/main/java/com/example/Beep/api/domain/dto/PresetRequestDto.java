package com.example.Beep.api.domain.dto;

import lombok.Getter;

@Getter
public class PresetRequestDto {

    Long uid;
    Integer number;
    Integer part;
    String content;
}
