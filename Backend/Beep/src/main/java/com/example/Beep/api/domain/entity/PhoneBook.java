package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhoneBook extends BaseEntity{

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(length = 11,nullable = false)
    private String targetPhone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer install;

    @Builder
    public PhoneBook(User user, String targetPhone, String name, Integer install) {
        this.user = user;
        this.targetPhone = targetPhone;
        this.name = name;
        this.install = install;
    }
}
