package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Relation24;
import org.springframework.data.repository.CrudRepository;

public interface Relation24Repository extends CrudRepository<Relation24, String> {
    //대화관계 존재 여부 조회
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
    //보낸사람id, 받는사람id로 조회
    Relation24 findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
