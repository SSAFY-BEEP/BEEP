package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.Message24Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class Message24ServiceImpl implements  Message24Service{
    private final Message24Repository repository;

    @Override
    public List<Message24> getReceiveMessage(Long receiver) {
        return repository.findAllByReceiver(receiver);
    }

    @Override
    public List<Message24> getSendMessage(Long sender) {
        return repository.findAllBySender(sender);
    }

    @Transactional
    @Override
    public void saveMessage(Message24RequestDto.sendMessage message) {
//        System.out.println(message.getId()+","+ message.getReceiver()+","+ message.getDistinction());
//        Message24 message24 = repository.findById(message.getId()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        
        //구분자 설정
        
        Message24 message24 = Message24.builder()
                .audioUri(message.getAudioUri())
                .content(message.getContent())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .distinction(1)
                .build();

        repository.save(message24);
    }

    @Override
    public List<Message24> getAllMessage() {
        return repository.findAll();
    }

    @Override
    public Message24 getMessage(String id) {
        return repository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
    }


    @Override
    public void deleteMessageById(String id) {
        repository.deleteById(id);
    }


}
