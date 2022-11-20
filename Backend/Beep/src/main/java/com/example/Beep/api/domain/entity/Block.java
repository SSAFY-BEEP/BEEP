package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseEntity{

    @JoinColumn(name = "message_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Message message;


    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User target;

    @Builder
    public Block(Message message,User user,User target){
        this.message = message;
        this.user=user;
        this.target=target;
    }
}
