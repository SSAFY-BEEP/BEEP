package com.example.Beep.api.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public BaseEntity(){}
    public BaseEntity(Long id) {
        this.id = id;
    }
}
