package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class PhoneBookResponseDto {
    @Getter
    @Builder
    public static class Phone{
        String name;
        String phone;
        Integer install;
    }
}
