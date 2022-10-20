package com.example.Beep.api.domain.entity;

import com.example.Beep.api.domain.entity.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class Dictionary extends BaseEntity {
    @Column(length = 11, nullable = false)
    private String word;

    @Column(nullable = false)
    private String value;
}