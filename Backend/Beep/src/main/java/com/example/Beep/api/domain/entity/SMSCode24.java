package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "smsCode24", timeToLive = 60*3)  //3분 유효시간
public class SMSCode24 {
    @Id
    private String id;

    //인증받을 핸드폰 번호
    @Indexed
    private String phoneNumber;

    //인증번호
    @Indexed
    private String code;

    private LocalDateTime time;

    @Builder
    public SMSCode24(String id,String phoneNumber, String code) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.time = LocalDateTime.now();
    }
}
