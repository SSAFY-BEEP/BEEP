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
public class Message extends BaseEntity{
    @Column(length = 11, nullable = false)
    private String content;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime time;

    @Column
    private String audioUri;

    @Column
    @ManyToOne
    private User senderId;

    @Column
    @ManyToOne
    private User receiverId;

    //1-보관메세지, 2-차단메세지지
    @Column
    private int type;

    @Column
    private int distinction;
}
