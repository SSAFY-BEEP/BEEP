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

    //보낸 사람 전화번호
    @Indexed
    private String senderNum;

    //받는 사람 전화번호
    @Indexed
    private String receiverNum;

    //해당데이터 소유자 전화번호
    @Indexed
    private String ownerNum;

    //0-일반메세지, 1-보관메세지, 2-차단메세지
    private Integer type;


    @Builder
    public Message24(String content, String audioUri, String senderNum, String receiverNum, String ownerNum, Integer type) {
        this.time = LocalDateTime.now();
        this.content = content;
        this.audioUri = audioUri;
        this.senderNum = senderNum;
        this.receiverNum = receiverNum;
        this.ownerNum = ownerNum;
        this.type = type == null? 0 : type;
    }
}
