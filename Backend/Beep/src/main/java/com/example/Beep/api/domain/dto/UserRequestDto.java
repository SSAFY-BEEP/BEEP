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

    @Getter
    @Builder
    public static class Block{
        @ApiModelProperty(value = "차단하는 유저")
        Long userId; //차단하는 유저
        @ApiModelProperty(value = "차단당하는 유저")
        Long targetId; //차단 당하는 유저
    }
}
