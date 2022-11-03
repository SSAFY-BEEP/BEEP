package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    @Override
    public List<BlockResponseDto> getList() {
        List<Block> list = blockRepository.findAll();

        List<BlockResponseDto> result = new ArrayList<>();

        for(Block b : list){
            result.add(BlockResponseDto.builder()
                    .id(b.getId())
                    .userId(b.getUser().getId())
                    .targetId(b.getTarget().getId())
                    .build()
            );
        }

        return result;
    }

    @Override
    public boolean isBlocked(String userNum, String targetNum) {
        User findUser = userRepository.findByPhoneNumber(userNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User findTarget = userRepository.findByPhoneNumber(targetNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        return blockRepository.existsByUserAndTarget(findUser, findTarget);
    }

    @Override
    @Transactional
    public void blockUser(Long messageId) {
        try{
            Message message= messageRepository.findById(messageId).get();
            User sender=userRepository.findById(message.getSender().getId()).get();
            User receiver=userRepository.findById(message.getReceiver().getId()).get();
            if(message.getOwnerId()==sender.getId()){
                Block newBlock= Block.builder()
                        .user(message.getSender())
                        .target(message.getReceiver())
                        .build();
                blockRepository.save(newBlock);
            }else{
                Block newBlock= Block.builder()
                        .user(message.getReceiver())
                        .target(message.getSender())
                        .build();
                blockRepository.save(newBlock);
            }
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void blockDelete(Long messageId) {
        try{
            Message message= messageRepository.findById(messageId).get();
            User sender=userRepository.findById(message.getSender().getId()).get();
            User receiver=userRepository.findById(message.getReceiver().getId()).get();
            if(message.getOwnerId()==sender.getId()){
                blockRepository.deleteByUserAndTarget(sender,receiver);
            }else{
                blockRepository.deleteByUserAndTarget(receiver,sender);
            }
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }
}
