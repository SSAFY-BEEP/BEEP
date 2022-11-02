package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "message24", timeToLive = 60*60*24)
public class Message24{
    @Id
    private String id;

    //메세지 내용
    private String content;

    //보낸시간
    private LocalDateTime time;

    //음성메세지
    private String audioUri;

    //보낸 사람
    @Indexed
    private Long sender;

    //받는 사람
    @Indexed
    private Long receiver;

    //1-보관메세지, 2-차단메세지
    private Integer type;

    //익명 내에서 구분
    private Integer distinction;

    @Builder
    public Message24(String content, String audioUri, Long sender, Long receiver, Integer type, Integer distinction) {
        this.time = LocalDateTime.now();
        this.content = content;
        this.audioUri = audioUri;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type == null? 0 : type;
        this.distinction = distinction;
    }
}
