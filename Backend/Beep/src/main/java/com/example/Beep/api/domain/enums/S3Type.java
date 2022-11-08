package com.example.Beep.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum S3Type {
    TEMP(1),
    PERMANENT(2);

    private final Integer Num;
}