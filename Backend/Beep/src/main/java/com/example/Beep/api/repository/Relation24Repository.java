package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.Relation24;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface Relation24Repository extends CrudRepository<Relation24, String> {

    List<Relation24> findAll();

    //대화관계 존재 여부 조회
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
    //보낸사람id, 받는사람id로 조회
    Optional<Relation24> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
