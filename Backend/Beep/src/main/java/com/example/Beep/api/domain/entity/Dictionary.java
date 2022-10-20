package com.example.Beep.api.domain.entity;

import com.example.Beep.api.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dictionary extends BaseEntity {
    @Column(length = 11, nullable = false)
    private String word;

    @Column(nullable = false)
    private String value;
}