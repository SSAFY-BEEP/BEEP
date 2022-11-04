package com.example.Beep.api.service;

import com.amazonaws.services.s3.model.Owner;
import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

//    @Override
//    public void saveSendMessage(MessageRequestDto.persistMessage persistMessage) {
//        try{
//            User sendUser=userRepository.findByPhoneNumber(persistMessage.getSenderPhoneNumber()).get();
//            User receiveUser=userRepository.findByPhoneNumber(persistMessage.getReceiverPhoneNumber()).get();
//
//            Message sendMessage=Message.builder()
//                    .audioUri(persistMessage.getAudioUri())
//                    .content(persistMessage.getContent())
//                    .receiver(receiveUser)
//                    .sender(sendUser)
//                    .time(persistMessage.getLocalDateTime())
//                    .ownerId(sendUser.getId())
//                    .tag(persistMessage.getTag())
//                    .build();
//            messageRepository.save(sendMessage);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public void saveReceiveMessage(MessageRequestDto.persistMessage persistMessage){
//        try{
//            User sendUser=userRepository.findByPhoneNumber(persistMessage.getSenderPhoneNumber()).get();
//            User receiveUser=userRepository.findByPhoneNumber(persistMessage.getReceiverPhoneNumber()).get();
//
//            Message sendMessage=Message.builder()
//                    .audioUri(persistMessage.getAudioUri())
//                    .content(persistMessage.getContent())
//                    .receiver(receiveUser)
//                    .sender(sendUser)
//                    .time(persistMessage.getLocalDateTime())
//                    .ownerId(receiveUser.getId())
//                    .tag(persistMessage.getTag())
//                    .build();
//            messageRepository.save(sendMessage);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void deleteMessage(Long messageId) {
        try{
            messageRepository.deleteById(messageId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<MessageResponseDto> findSendMessage() {
        try{
            String userId = SecurityUtil.getCurrentUsername().get();

            User user=userRepository.findByPhoneNumber(userId).get();
            List<MessageResponseDto>messageResponseDtoList=messageRepository.findSendMessage(user.getId()).stream()
                    .map(Message->MessageResponseDto.builder()
                            .id(Message.getId())
                            .content(Message.getContent())
                            .receiverPhoneNumber(Message.getReceiver().getPhoneNumber())
                            .senderPhoneNumber(Message.getSender().getPhoneNumber())
                            .audioUri(Message.getAudioUri())
                            .localDateTime(Message.getTime())
                            .tag(Message.getTag())
                            .build()).collect(Collectors.toList());
            return messageResponseDtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MessageResponseDto> findReceiveMessageByType(Integer type) {
        try{
            String userId = SecurityUtil.getCurrentUsername().get();

            User user=userRepository.findByPhoneNumber(userId).get();
            List<MessageResponseDto>messageResponseDtoList=messageRepository.findReceiveMessageByType(user.getId(), type).stream()
                    .map(Message->MessageResponseDto.builder()
                            .id(Message.getId())
                            .content(Message.getContent())
                            .receiverPhoneNumber(Message.getReceiver().getPhoneNumber())
                            .senderPhoneNumber(Message.getSender().getPhoneNumber())
                            .audioUri(Message.getAudioUri())
                            .localDateTime(Message.getTime())
                            .tag(Message.getTag())
                            .build()).collect(Collectors.toList());
            return messageResponseDtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateTag(MessageRequestDto.updateTag updateTag){
        Message message=messageRepository.findById(updateTag.getId()).get();
        message.Update(updateTag.getTag());
        messageRepository.save(message);
    }
}