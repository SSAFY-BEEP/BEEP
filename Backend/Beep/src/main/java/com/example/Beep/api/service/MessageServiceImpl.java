package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public void saveMessage(MessageRequestDto.sendMessage sendMessage) {
        try{
            User sendUser=userRepository.findByPhoneNumber(sendMessage.getSenderPhoneNumber()).get();
            User receiveUser=userRepository.findByPhoneNumber(sendMessage.getReceiverPhoneNumber()).get();

            Message message=Message.builder()
                    .audioUri(sendMessage.getAudioUri())
                    .content(sendMessage.getContent())
                    .receiver(receiveUser)
                    .sender(sendUser)
                    .build();
            messageRepository.save(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteMessage(MessageRequestDto.sendMessage sendMessage) {
        try{
            User sendUser=userRepository.findByPhoneNumber(sendMessage.getSenderPhoneNumber()).get();
            User receiveUser=userRepository.findByPhoneNumber(sendMessage.getReceiverPhoneNumber()).get();
            System.out.println(sendUser.getId()+" "+receiveUser.getId()+"번호");
            Message message=messageRepository.findMessage(sendUser.getId(), receiveUser.getId());
            messageRepository.deleteById(message.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
