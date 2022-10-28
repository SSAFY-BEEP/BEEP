package com.example.Beep.api.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

public class UserRequestDto {

    @Getter
    @Builder
    public static class SignUp {
        @ApiModelProperty(example = "01012345678")
        String phoneNumber;
        @ApiModelProperty(example = "1234asdf")
        String password;
        @ApiModelProperty(example = "토큰")
        String fcmToken;
    }

    @Getter
    @Builder
    public static class Login {
        String phoneNumber;
        String password;
    }
}
