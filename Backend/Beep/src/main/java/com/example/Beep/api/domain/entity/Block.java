package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block extends BaseEntity{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User target;

    @Builder
    public Block(User user1,User user2){
        this.user=user1;
        this.target=user2;
    }

    public void Update(User Puser, User Buser) {
        this.user=Puser;
        this.target=Buser;
    }
}
