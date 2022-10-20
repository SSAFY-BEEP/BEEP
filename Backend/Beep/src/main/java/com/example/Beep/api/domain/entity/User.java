package com.example.Beep.api.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class User extends BaseEntity{

    @Column(length = 11,unique = true)
    private String phone_number;

    @Column
    private String password;

    @Column
    private String introduceAudio; //녹음 주소 url

    @Column(length = 10)
    private String engrave;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private int theme;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private int font;

    @Column(nullable = false)
    @ColumnDefault("1") //각각의 번호
    private int alarm;

    @Column(nullable = false)
    @ColumnDefault("1")  //1이면 회원, 0이면 회원탈퇴
    private int active;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Preset> presetList=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PhoneBook>phoneBookList=new ArrayList<>();
}
