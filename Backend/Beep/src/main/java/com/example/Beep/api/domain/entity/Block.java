package com.example.Beep.api.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Block extends BaseEntity{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @JoinColumn(name = "target_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User targetId;
}
