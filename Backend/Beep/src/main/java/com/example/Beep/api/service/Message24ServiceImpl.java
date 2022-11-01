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
    public List<Message24> getReceiveMessage(Long receiverId) {
        List<Message24> list = repository.findAllByReceiverAndOwnerId(receiverId, receiverId);

        List<Message24> result = new ArrayList<>();

        //차단 type은 2 -> 차단이 아닌 메세지 조회
        for(Message24 cur: list) {
            if (cur.getType() != 2) {
                result.add(cur);
            }
        }
        return result;
    }

    @Override
    public List<Message24> getSendMessage(Long senderId) {
        return repository.findAllBySenderAndOwnerId(senderId, senderId);
    }

    @Transactional
    @Override
    public void sendMessage(Message24RequestDto.sendMessage message) {
        //보낸사람, 받은사람 기준으로 데이터 2번 저장
        Message24 senderMsg = Message24.builder()
                .ownerId(message.getSender())
                .audioUri(message.getAudioUri())
                .content(message.getContent())
                .senderId(message.getSender())
                .receiverId(message.getReceiver())
                .build();
        repository.save(senderMsg);

        Message24 receiverMsg = Message24.builder()
                .ownerId(message.getReceiver())
                .audioUri(message.getAudioUri())
                .content(message.getContent())
                .senderId(message.getSender())
                .receiverId(message.getReceiver())
                .build();

        repository.save(receiverMsg);
    }

    //메세지 보관
    @Override
    public void changeMessageType(String id, Long ownerId, Integer type) {
        Message24 find = repository.findById(id).get();
        User sender = userRepository.findById(find.getSenderId()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User receiver = userRepository.findById(find.getReceiverId()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //레디스 type 1(보관)로 수정
        Message24 message24 = Message24.builder()
                .ownerId(find.getOwnerId())
                .content(find.getContent())
                .senderId(find.getSenderId())
                .receiverId(find.getReceiverId())
                .audioUri(find.getAudioUri())
                .type(type)
                .build();
        repository.save(message24);

       //해당 메세지 DB 보관하기
        Message message = Message.builder()
                .ownerId(find.getOwnerId())
                .content(find.getContent())
                .audioUri(find.getAudioUri())
                .sender(sender)
                .receiver(receiver)
                .type(type)
                .build();

        messageRepository.save(message);
    }


    //모든 메세지
    @Override
    public List<Message24> getAllMessage() {
        return repository.findAll();
    }

    //해당 메세지 id의 메세지 데이터
    @Override
    public Message24 getMessage(String id) {
        return repository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
    }


    @Override
    public void deleteMessageById(String id, Long ownerId) {
        repository.deleteByIdAndOwnerId(id, ownerId);
    }
}
