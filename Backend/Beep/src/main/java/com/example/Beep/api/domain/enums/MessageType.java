package com.example.Beep.api.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MessageType {
    SAVE("SAVE", 1),
    BLOCK("BLOCK", 2);

    private final String Name;
    private final Integer Num;
}