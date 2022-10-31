package com.example.Beep.api.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity{

    @Column(length = 11,unique = true)
    private String phoneNumber;

    @Column
    private String password;

    @Column
    private String introduceAudio; //녹음 주소 url

    @Column(length = 10)
    private String engrave;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private Integer theme;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private Integer font;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private Integer alarm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Authority authority;

    @Column(nullable = false)      //가입을 할 때 토큰을 받아와야 함
    @ColumnDefault("0")     //토큰이 없는 상태
    private String fcmToken;

    @OneToMany(mappedBy = "user",cascade = ALL )
    private List<Preset> presetList=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = ALL)
    private List<PhoneBook>phoneBookList=new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)        //차단하는 사람을 기준으로 한 블록리스트
    private List<Block> blockList = new ArrayList<>();

    @Builder
    public User(String phoneNumber, String password, String fcmToken, Authority authority) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fcmToken = fcmToken;
        this.authority = authority;
    }
    public User update(String phoneNumber, String password, String fcmToken, Authority authority) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fcmToken = fcmToken;
        this.authority = authority;
        return this;
    }

    public User findPw(String password) {
        this.password = password;
        return this;
    }

    @PrePersist
    public void prePersist() {
        this.alarm = this.alarm == null ? 1 : this.alarm;
        this.theme = this.theme == null ? 1 : this.theme;
        this.font = this.font == null ? 1 : this.font;
        this.authority = this.authority == null ? Authority.ROLE_USER : this.authority;
    }
}
