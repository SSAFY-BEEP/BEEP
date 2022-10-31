package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "relation24", timeToLive = 60*60*24)
public class Relation24{
    @Id
    private String id;

    private Long senderId;

    private Long receiverId;

    private LocalDateTime time;

    @Builder
    public Relation24(Long senderId, Long receiverId, LocalDateTime time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = LocalDateTime.now();
    }
}
