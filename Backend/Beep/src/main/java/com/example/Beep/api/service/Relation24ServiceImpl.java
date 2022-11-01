package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.dto.Relation24RequestDto;
import com.example.Beep.api.domain.entity.Relation24;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.Relation24Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Relation24ServiceImpl implements Relation24Service{

    private final Relation24Repository repository;

    @Override
    public List<Relation24> getList() {
        return repository.findAll();
    }

    @Override
    public boolean existsRelation(Long senderId, Long receiverId) {
        return repository.existsBySenderIdAndReceiverId(senderId,receiverId);
    }

    @Override
    public void insertRelation(Message24RequestDto.sendMessage message) {
        System.out.println(message.getSender()+","+ message.getReceiver());

        Optional<Relation24> findRelation =repository.findBySenderIdAndReceiverId(message.getSender(), message.getReceiver());

        Relation24 relation24 = Relation24.builder()
                .id(findRelation.isPresent()? findRelation.get().getId() : null)
                .senderId(message.getSender())
                .receiverId(message.getReceiver())
                .build();

        repository.save(relation24);
    }

    @Override
    public Relation24 findBySenderAndReceiver(Long senderId, Long receiveId) {
        return repository.findBySenderIdAndReceiverId(senderId, receiveId).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
    }
}
