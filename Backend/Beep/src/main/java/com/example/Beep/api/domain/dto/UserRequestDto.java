package com.example.Beep.api.domain.dto;

import com.example.Beep.api.domain.enums.Authority;
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
        @ApiModelProperty(example = "01074794185")
        String phoneNumber;
        @ApiModelProperty(example = "12345asdf")
        String password;
    }


    @Getter
    @Builder
    public static class CreateUser {
        @ApiModelProperty(example = "01012345678")
        String phoneNumber;
        String password;
        @ApiModelProperty(example = "url")
        String introduceAudio;
        String engrave;
        @ApiModelProperty(example = "1")
        Integer theme;
        @ApiModelProperty(example = "1")
        Integer font;
        @ApiModelProperty(example = "1")
        Integer alarm;
        @ApiModelProperty(example = "토큰")
        String fcmToken;
        @ApiModelProperty(example = "ROLE_USER")
        Authority authority;
    }

}
