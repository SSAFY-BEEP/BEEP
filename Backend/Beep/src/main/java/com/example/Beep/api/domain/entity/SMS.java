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
@DynamicInsert
public class SMS extends BaseEntity{
    @Column
    @ManyToOne
    private User senderId;

    @Column     //받는사람
    @ManyToOne
    private User receiverId;

    @Column(nullable = false, length = 11)  //내용
    private String content;

    @Column(nullable = false)   //보낸 시간
    private LocalDateTime time;

    @Column(nullable = false)   //음성메세지 주소
    private String audio_uri;
}
