package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class PhoneBookRequestDto {

    @Getter
    @Builder
    public static class Register{
        String name;
        String phone;
    }
}
