package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.dto.S3RequestDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.domain.enums.S3Type;
import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.Message24Repository;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
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
    private final S3Service s3Service;


    //받은 메세지 조회(보관, 차단)
    //보관만
    @Override
    public List<Message24> getReceiveMessage() {
        String receiverNum = SecurityUtil.getCurrentUsername().get();
        List<Message24> list = repository.findAllByReceiverNumAndOwnerNum(receiverNum, receiverNum);

        List<Message24> result = new ArrayList<>();

        //차단이 아닌 메세지 조회(일반, 보관)
        for(Message24 cur: list) {
            if (cur.getType() != MessageType.BLOCK.getNum()) {
                result.add(cur);
            }
        }
        return result;
    }

    //보낸 메세지 조회
    @Override
    public List<Message24> getSendMessage() {
        String senderNum = SecurityUtil.getCurrentUsername().get();
        return repository.findAllBySenderNumAndOwnerNum(senderNum, senderNum);
    }

//    //메세지 24에 메세지 저장
//    @Transactional
//    @Override
//    public void sendMessage(Message24RequestDto.sendMessage message, boolean isBlocked) {
//        String userNum = SecurityUtil.getCurrentUsername().get();
//
//        //보낸사람, 받은사람 기준으로 데이터 2번 저장
//        //보낸사람에게 저장
//        Message24 senderMsg = Message24.builder()
//                .ownerNum(userNum)
//                .audioUri(message.getAudioUri())
//                .content(message.getContent())
//                .senderNum(userNum)
//                .receiverNum(message.getReceiverNum())
//                .build();
//        repository.save(senderMsg);
//
//        if(!isBlocked){ //차단 안됐을 경우에만 수신자에게도 전해짐
//            Message24 receiverMsg = Message24.builder()
//                    .ownerNum(message.getReceiverNum())
//                    .audioUri(message.getAudioUri())
//                    .content(message.getContent())
//                    .senderNum(userNum)
//                    .receiverNum(message.getReceiverNum())
//                    .build();
//
//            repository.save(receiverMsg);
//        }
//    }
    @Transactional
    @Override
    public void sendMessageWithFile(S3RequestDto.sendMessage24 message, boolean isBlocked) {
        String userNum = SecurityUtil.getCurrentUsername().get();

        //S3파일 저장
        String audioFileForSender = s3Service.uploadFile(message.getFile());

        //보낸사람, 받은사람 기준으로 데이터 2번 저장
        //보낸사람에게 저장
        Message24 senderMsg = Message24.builder()
                .ownerNum(userNum)
                .audioUri(audioFileForSender)
                .content(message.getContent())
                .senderNum(userNum)
                .receiverNum(message.getReceiverNum())

        //초대메세지를 보내야하지 않을까?
        User receiver = userRepository.findByPhoneNumber(message.getReceiverNum())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        // 받을 상대의 fcm 토큰
        String registrationToken = receiver.getFcmToken();
        // See documentation on defining a message payload.
        com.google.firebase.messaging.Message fcmMessage = com.google.firebase.messaging.Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("[BEEP] 테스트 메시지")
                        .setBody("테스트 입니다.")
                        .build())
//                .putData("title", "테스트 메시지")
//                .putData("content", "안녕하세요")
                .setToken(registrationToken)
                .build();


        // Send a message to the device corresponding to the provided
        // registration token.
        try{
            String response = FirebaseMessaging.getInstance().send(fcmMessage);
            // 성공하면 메시지 아이디를 반환함
            System.out.println("Successfully sent message: " + response);

            if(!isBlocked){ //차단 안됐을 경우에만 수신자에게도 전해짐
                //S3파일 저장
                String audioFileForReceiver = s3Service.uploadFile(message.getFile());

                Message24 receiverMsg = Message24.builder()
                        .ownerNum(message.getReceiverNum())
                        .audioUri(audioFileForReceiver)
                        .content(message.getContent())
                        .senderNum(userNum)
                        .receiverNum(message.getReceiverNum())
                        .build();

                repository.save(receiverMsg);
            }
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return;
        }
    }

    //메세지 보관
    @Override
    @Transactional
    public Long changeMessageType(String messageId, Integer type) {

        Message24 find = repository.findById(messageId).get();
        User sender = userRepository.findByPhoneNumber(find.getSenderNum()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User receiver = userRepository.findByPhoneNumber(find.getReceiverNum()).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //차단을 하는 사람
        String ownerNum = SecurityUtil.getCurrentUsername().get();
        User owner = userRepository.findByPhoneNumber(ownerNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //해당 메세지의 type이 현재 type이랑 같으면 에러(중복 보관/차단이니까)
        if(find.getType() == type){
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        }

        //레디스 type을 1(보관)/2(차단)로 수정
        find.updateType(type);
        repository.save(find);

        //S3에 보관
        s3Service.copyFile(messageId);

        //해당 메세지 DB 보관하기
        Message message = Message.builder()
                .owner(owner)
                .time(find.getTime())
                .content(find.getContent())
                .audioUri(find.getAudioUri())
                .sender(sender)
                .receiver(receiver)
                .type(type)
                .build();

        Message result = messageRepository.save(message);
        return result.getId();
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


    //24시간 메세지 삭제
    @Override
    @Transactional
    public void deleteMessageById(String id) {
        String userNum = SecurityUtil.getCurrentUsername().get();
        //해당 메세지id로 메세지 삭제
        Message24 message24 = repository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.BAD_REQUEST));
        
        //삭제하는 유저가 해당 메세지 데이터 소유자가 아니면 에러
        if(message24.getOwnerNum() != userNum) throw new CustomException(ErrorCode.BAD_REQUEST);

        //음성파일 존재하면 S3파일 삭제
        if(message24.getAudioUri()!=null){
            s3Service.deleteFile(message24.getAudioUri(), S3Type.TEMP.getNum());
        }

        //DB에서 삭제
        repository.deleteById(id);
    }
}
