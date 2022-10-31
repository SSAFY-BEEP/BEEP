package com.example.Beep.api.service;

import com.example.Beep.api.domain.entity.Relation24;
import com.example.Beep.api.repository.Relation24Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Relation24ServiceImpl implements Relation24Service{

    private final Relation24Repository repository;

    @Override
    public boolean existsRelation(Long senderId, Long receiverId) {
        return repository.existsBySenderIdAndReceiverId(senderId,receiverId);
    }

    @Override
    public void insertRelation(Long senderId, Long receiverId) {
//        if(!repository.existsBySenderIdAndReceiverId(senderId,receiverId)){
//            Relation24 relation24 = Relation24.builder()
//                    .senderId(senderId)
//                    .receiverId(receiverId)
//                    .build();
//        } else{
//            Relation24 relatio
//        }



        repository.save(relation24);
    }

    @Override
    public void updateRelationTime(Long senderId, Long receiverId) {
        Relation24 relation24 = repository.findBySenderIdAndReceiverId(senderId,receiverId);

        repository.save(relation24);
    }
}
