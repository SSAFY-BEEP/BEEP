package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Message extends BaseEntity{
    //메세지 내용
    @Column(length = 11, nullable = false)
    private String content;

    //보낸시간
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    //음성메세지
    @Column
    private String audioUri;

    //보낸 사람
    @JoinColumn(name="sender_id")
    @ManyToOne
    private User sender;

    //받는 사람
    @JoinColumn(name="receiver_id")
    @ManyToOne
    private User receiver;

    //1-보관메세지, 2-차단메세지지
    @Column
    private Integer type;


    @Builder
    public Message(String content, String audioUri, User sender, User receiver, Integer type) {
        this.time = LocalDateTime.now();
        this.content = content;
        this.audioUri = audioUri;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type == null? 1 : type;
    }
}
