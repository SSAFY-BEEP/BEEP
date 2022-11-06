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

    @Column
    private Long ownerId;

    @Column(length=5,nullable = true)
    private String tag;


    @Builder
    public Message(String content, String audioUri, User sender, User receiver, Integer type,LocalDateTime time,Long ownerId,String tag) {
        this.time = time;
        this.content = content;
        this.audioUri = audioUri;
        this.sender = sender;
        this.receiver = receiver;
        this.ownerId=ownerId;
        this.type = type == null? 1 : type;
        this.tag=tag;
    }

    public Message Update(String tag){
        this.tag=tag;
        return this;
    }

    public Message Block(Integer type){
        this.type=type;
        return this;
    }
}
