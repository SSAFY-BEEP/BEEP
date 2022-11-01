package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.Message24Repository;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Message24ServiceImpl implements  Message24Service{
    private final Message24Repository repository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    @Override
    public List<Message24> getReceiveMessage(Long receiver) {
        List<Message24> list = repository.findAllByReceiver(receiver);

        List<Message24> result = new ArrayList<>();

        //차단 type은 2 > 차단이 아닌 메세지 조회
        for(Message24 cur: list) {
            if (cur.getType() != 2) {
                result.add(cur);
            }
        }
        return result;
    }

    @Override
    public List<Message24> getSendMessage(Long sender) {
        return repository.findAllBySender(sender);
    }

    @Transactional
    @Override
    public void sendMessage(Message24RequestDto.sendMessage message) {
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

    //메세지 보관
    @Override
    public void saveMessage(String id) {
        Message24 message24 = repository.findById(id).get();
        User sender = userRepository.findById(message24.getSender()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User receiver = userRepository.findById(message24.getReceiver()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //구분자 설정

        Message message = Message.builder()
                .content(message24.getContent())
                .audioUri(message24.getAudioUri())
                .sender(sender)
                .receiver(receiver)
                .type(1)
                .distinction(message24.getDistinction())
                .build();

        //해당 메세지 DB 보관하기
        messageRepository.save(message);
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
