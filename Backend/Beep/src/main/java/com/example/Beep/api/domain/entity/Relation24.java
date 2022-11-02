package com.example.Beep.api.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "relation24", timeToLive = 60*60*24)
public class Relation24{
    @Id
    private String id;

    @Indexed
    private Long senderId;

    @Indexed
    private Long receiverId;

    private LocalDateTime time;

    @Builder
    public Relation24(String id,Long senderId, Long receiverId) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = LocalDateTime.now();
    }
}


