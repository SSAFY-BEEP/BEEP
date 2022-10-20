package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Preset extends BaseEntity{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int part;

    @Column(length = 11,nullable = false)
    private String content;
}
