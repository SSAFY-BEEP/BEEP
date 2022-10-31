package com.example.Beep.api.service;

public interface Relation24Service {
    //대화관계 존재여부
    boolean existsRelation(Long senderId, Long receiverId);

    //대화관계 입력
    void insertRelation(Long senderId, Long receiverId);

    //대화시간 갱신
    void updateRelationTime(Long senderId, Long receiverId);
}
