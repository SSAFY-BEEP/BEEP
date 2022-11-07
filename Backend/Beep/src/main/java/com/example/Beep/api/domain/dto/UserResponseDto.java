package com.example.Beep.api.domain.dto;

import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Token {
        private String token;
    }

    @Getter
    @NoArgsConstructor
    public static class UserDto {
        private Long id;
        private String phoneNumber;
        private String password;
        private String introduceAudio; //녹음 주소 url
        private String engrave;
        private Integer theme;
        private Integer font;
        private Integer alarm;
        private Authority authority;
        private String fcmToken;

        public UserDto of(User user) {
            this.id = user.getId();
            this.phoneNumber = user.getPhoneNumber();
            this.password = user.getPassword();
            this.introduceAudio = user.getIntroduceAudio();
            this.engrave = user.getEngrave();
            this.theme = user.getTheme();
            this.font = user.getFont();
            this.alarm = user.getAlarm();
            this.authority = user.getAuthority();
            this.fcmToken = user.getFcmToken();
            return this;
        }
    }
}
