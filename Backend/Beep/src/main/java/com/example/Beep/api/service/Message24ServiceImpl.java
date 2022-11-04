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
import com.example.Beep.api.security.SecurityUtil;
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
    public List<Message24> getReceiveMessage(String receiverNum) {
        List<Message24> list = repository.findAllByReceiverNumAndOwnerNum(receiverNum, receiverNum);

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
    public List<Message24> getSendMessage(String senderNum) {
        return repository.findAllBySenderNumAndOwnerNum(senderNum, senderNum);
    }

    //메세지 24에 메세지 저장
    @Transactional
    @Override
    public void sendMessage(Message24RequestDto.sendMessage message, boolean isBlocked) {
        String userNum = SecurityUtil.getCurrentUsername().get();

        //보낸사람, 받은사람 기준으로 데이터 2번 저장
        //보낸사람에게 저장
        Message24 senderMsg = Message24.builder()
                .ownerNum(userNum)
                .audioUri(message.getAudioUri())
                .content(message.getContent())
                .senderNum(userNum)
                .receiverNum(message.getReceiverNum())
                .build();
        repository.save(senderMsg);

        if(!isBlocked){ //차단 안됐을 경우에만 수신자에게도 전해짐
            Message24 receiverMsg = Message24.builder()
                    .ownerNum(message.getReceiverNum())
                    .audioUri(message.getAudioUri())
                    .content(message.getContent())
                    .senderNum(userNum)
                    .receiverNum(message.getReceiverNum())
                    .build();

            repository.save(receiverMsg);
        }
    }

    //메세지 보관
    @Override
    public void changeMessageType(String messageId, Integer type) {
        //메세지 존재시
        Message24 find = repository.findById(messageId).get();
        User sender = userRepository.findByPhoneNumber(find.getSenderNum()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User receiver = userRepository.findByPhoneNumber(find.getReceiverNum()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //차단을 하는 사람
        String ownerNum = SecurityUtil.getCurrentUsername().get();
        Long owner = userRepository.findByPhoneNumber(ownerNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND)).getId();

        //해당 메세지의 type이 현재 type이랑 같으면 에러(중복 보관o차단이니까)
        if(find.getType() == type){
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        }

        //레디스 type 1(보관)/2(차단)로 수정
        Message24 message24 = Message24.builder()
                .ownerNum(find.getOwnerNum())
                .content(find.getContent())
                .senderNum(find.getSenderNum())
                .receiverNum(find.getReceiverNum())
                .audioUri(find.getAudioUri())
                .type(type)
                .build();
        repository.save(message24);

        //해당 메세지 DB 보관하기
        Message message = Message.builder()
                .ownerId(owner)
                .time(find.getTime())
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
    public void deleteMessageById(String id) {
        String userNum = SecurityUtil.getCurrentUsername().get();
        repository.deleteByIdAndOwnerNum(id, userNum);
    }
}
