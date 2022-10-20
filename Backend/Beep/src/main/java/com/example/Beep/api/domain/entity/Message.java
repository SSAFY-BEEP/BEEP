package com.example.Beep.api.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Message extends BaseEntity{
    //메세지 내용
    @Column(length = 11, nullable = false)
    private String content;

    //보낸시간
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    //음성메세지
    @Column
    private String audioUri;

    //보낸 사람
    @Column
    @ManyToOne
    private User senderId;

    //받는 사람
    @Column
    @ManyToOne
    private User receiverId;

    //1-보관메세지, 2-차단메세지지
    @Column
    private int type;

    //익명 내에서 구분
    @Column
    private int distinction;
}
