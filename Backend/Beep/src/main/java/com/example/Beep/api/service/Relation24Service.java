package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.dto.Relation24RequestDto;
import com.example.Beep.api.domain.entity.Relation24;


import java.util.List;

public interface Relation24Service {
    //대화관계 전체 조회
    List<Relation24> getList();

    //대화관계 존재여부
    boolean existsRelation(Long senderId, Long receiverId);

    //대화관계 입력 및 수정
    void insertRelation(Message24RequestDto.sendMessage message);

    //대화관계 조건조회
    Relation24 findBySenderAndReceiver(Long senderId, Long receiveId);
}
