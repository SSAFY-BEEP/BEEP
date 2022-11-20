package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class SMSRequestDto {

    @Getter
    @Builder
    public static class Send{
        String targetPhone;
        String msg;
    }
}
