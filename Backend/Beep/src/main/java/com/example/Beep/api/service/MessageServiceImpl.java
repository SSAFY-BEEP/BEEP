package com.example.Beep.api.service;

import com.amazonaws.services.s3.model.Owner;
import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public void saveMessage(MessageRequestDto.persistMessage persistMessage) {
        try{
            User sendUser=userRepository.findByPhoneNumber(persistMessage.getSenderPhoneNumber()).get();
            User receiveUser=userRepository.findByPhoneNumber(persistMessage.getReceiverPhoneNumber()).get();

            Message sendMessage=Message.builder()
                    .audioUri(persistMessage.getAudioUri())
                    .content(persistMessage.getContent())
                    .receiver(receiveUser)
                    .sender(sendUser)
                    .time(persistMessage.getLocalDateTime())
                    .ownerId(sendUser.getId())
                    .build();

            Message receiveMessage=Message.builder()
                    .audioUri(persistMessage.getAudioUri())
                    .content(persistMessage.getContent())
                    .receiver(receiveUser)
                    .sender(sendUser)
                    .time(persistMessage.getLocalDateTime())
                    .ownerId(receiveUser.getId())
                    .build();

            messageRepository.save(sendMessage);
            messageRepository.save(receiveMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(MessageRequestDto.persistMessage persistMessage) {
        try{
            User sendUser=userRepository.findByPhoneNumber(persistMessage.getSenderPhoneNumber()).get();
            User receiveUser=userRepository.findByPhoneNumber(persistMessage.getReceiverPhoneNumber()).get();
            System.out.println(sendUser.getId()+" "+receiveUser.getId()+"번호");
            if(persistMessage.getSenderPhoneNumber().equals(persistMessage.getOwnerPhoneNumber())){
                Message message=messageRepository.findMessageSend(sendUser.getId(), receiveUser.getId());
                messageRepository.deleteById(message.getId());
            }else {
                Message message = messageRepository.findMessageReceive(sendUser.getId(), receiveUser.getId());
                messageRepository.deleteById(message.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<MessageResponseDto> findMessage(MessageRequestDto.persistMessage persistMessage) {
        try{
            User user=userRepository.findByPhoneNumber(persistMessage.getSenderPhoneNumber()).get();
            List<MessageResponseDto> messageResponseDtoList=messageRepository.messageList(user.getId()).stream()
                    .map(Message->MessageResponseDto.builder()
                            .content(persistMessage.getContent())
                            .localDateTime(persistMessage.getLocalDateTime())
                            .receiverPhoneNumber(persistMessage.getReceiverPhoneNumber())
                            .senderPhoneNumber(persistMessage.getSenderPhoneNumber())
                            .audioUri(persistMessage.getAudioUri())
                            .build()).collect(Collectors.toList());
            return messageResponseDtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MessageResponseDto> openMessage(MessageRequestDto.persistMessage persistMessage) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}