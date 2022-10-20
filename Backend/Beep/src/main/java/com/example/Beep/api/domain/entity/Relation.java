package com.example.Beep.api.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Relation extends BaseEntity{
    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User senderId;

    @JoinColumn(name = "receiver_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiverId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDate time;
}
