package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SMS extends BaseEntity{
    //보낸사람
    @Column
    @ManyToOne
    private User senderId;

    //받는사람
    @Column
    @ManyToOne
    private User receiverId;

    //내용
    @Column(nullable = false, length = 11)
    private String content;

    //보낸 시간
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    //음성메세지 주소
    @Column(nullable = false)
    private String audioUri;
}
