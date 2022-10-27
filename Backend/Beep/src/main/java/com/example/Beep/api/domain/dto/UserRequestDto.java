package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class UserRequestDto {

    @Getter
    @Builder
    public static class SignUp {
        String phoneNumber;
        String password;
        String fcmToken;
    }

    @Getter
    @Builder
    public static class Login {
        String phoneNumber;
        String password;
    }
}
