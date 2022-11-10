package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.domain.enums.S3Type;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final BlockRepository blockRepository;


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
    public List<MessageResponseDto> getAll() {

        List<MessageResponseDto> result = messageRepository.findAll().stream()
                .map(Message ->MessageResponseDto.builder()
                .id(Message.getId())
                .content(Message.getContent())
                .receiverPhoneNumber(Message.getReceiver().getPhoneNumber())
                .senderPhoneNumber(Message.getSender().getPhoneNumber())
                .ownerPhoneNumber(Message.getOwner().getPhoneNumber())
                .audioUri(Message.getAudioUri())
                .localDateTime(Message.getTime())
                .tag(Message.getTag())
                .type(Message.getType())
                .build()).collect(Collectors.toList());

        return result;
    }

    @Transactional
    @Override
    public void deleteMessage(Long messageId) {
        try{
            //음성파일 있으면 S3 삭제
            Message message = messageRepository.findById(messageId).orElseThrow(()-> new CustomException(ErrorCode.METHOD_NO_CONTENT));
            if(message.getAudioUri()!=null){
                s3Service.deleteFile(message.getAudioUri(), S3Type.PERMANENT.getNum());
            }

            //차단관계 삭제
            blockRepository.deleteByMessage(message);

            //DB에서 삭제
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
                            .type(Message.getType())
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
                            .ownerPhoneNumber(Message.getOwner().getPhoneNumber())
                            .receiverPhoneNumber(Message.getReceiver().getPhoneNumber())
                            .senderPhoneNumber(Message.getSender().getPhoneNumber())
                            .audioUri(Message.getAudioUri())
                            .localDateTime(Message.getTime())
                            .tag(Message.getTag())
                            .type(Message.getType())
                            .build()).collect(Collectors.toList());
            return messageResponseDtoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //보관/차단 메세지 타입 변경
    @Transactional
    @Override
    public String changeMessageType(Long messageId,Integer type) {
        Message message = messageRepository.findById(messageId).orElseThrow(()-> new CustomException(ErrorCode.METHOD_NO_CONTENT));

        //바꾸려는 타입이랑 현재타입이 같으면 에러!
        if(type == message.getType()) throw new CustomException(ErrorCode.METHOD_ALREADY_REPORTED);

        //차단->보관 이면 차단관계 삭제
        else if(message.getType()==2 && type==1){
            //차단관계 삭제
            blockRepository.deleteByMessage(message);
        }

        message.changeType(type);
        messageRepository.save(message);

        return type==1?"보관":"차단"+"메세지로 변경 완료";
    }

    public void updateTag(MessageRequestDto.updateTag updateTag){
        Message message=messageRepository.findById(updateTag.getId()).get();
        message.Update(updateTag.getTag());
        messageRepository.save(message);
    }
}