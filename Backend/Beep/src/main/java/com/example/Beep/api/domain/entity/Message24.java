package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@RedisHash(value = "message24", timeToLive = 60*60*24)
public class Message24{
    @Id
    private Long id;

    //메세지 내용
    private String content;

    //보낸시간
    private LocalDateTime time;

    //음성메세지
    private String audioUri;

    //보낸 사람
    private Long sender;

    //받는 사람
    private Long receiver;

    //1-보관메세지, 2-차단메세지지
    private Integer type;

    //익명 내에서 구분
    private Integer distinction;

    public Message24(Long id,String content, String audioUri, Long sender, Long receiver, Integer type, Integer distinction) {
        this.id = id;
        this.content = content;
        this.audioUri = audioUri;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.distinction = distinction;
    }
}
