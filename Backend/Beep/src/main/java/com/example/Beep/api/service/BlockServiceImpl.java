package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.Message;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
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
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    private final Message24Repository message24Repository;

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
    public boolean isBlocked(String targetNum) {
        String ownerNum = SecurityUtil.getCurrentUsername().orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User findUser = userRepository.findByPhoneNumber(ownerNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User findTarget = userRepository.findByPhoneNumber(targetNum).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        return blockRepository.existsByUserAndTarget(findUser, findTarget);
    }

    //메세지24로 차단하기
    @Override
    @Transactional
    public void blockUser(String messageId) {
        try{
            String ownerNum = SecurityUtil.getCurrentUsername().get();

            Message24 message24= message24Repository.findById(messageId).get();

            User sender=userRepository.findByPhoneNumber(message24.getSenderNum()).get();
            User receiver=userRepository.findByPhoneNumber(message24.getReceiverNum()).get();

            //토큰 유저랑 같은 사람이 user , 다른 사람이 target
            Block newBlock= Block.builder()
                    .user(ownerNum==sender.getPhoneNumber()? sender : receiver)
                    .target(ownerNum!=sender.getPhoneNumber()? sender : receiver)
                    .build();
            blockRepository.save(newBlock);
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void blockDelete(Long messageId) {
        try{
            //해당 메세지 id로 차단id 찾아서 삭제
            Message message= messageRepository.findById(messageId).get();

            blockRepository.deleteByMessage(message);
        }catch (NullPointerException n){
            throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
        }
    }

    @Override
    public String blockByMsgId(String message24Id) {
        Message24 message24 = message24Repository.findById(message24Id).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));

        User user = userRepository.findByPhoneNumber(message24.getOwnerNum()).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User target = userRepository.findByPhoneNumber(message24.getSenderNum()).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //존재하는 차단관계인지 확인
        if(blockRepository.existsByUserAndTarget(user, target)){ //이미 존재
            return "이미 차단된 사용자입니다.";
        } else{ //안 존재
            //차단 관계 설정
            Block block = Block.builder()
                    .user(user)
                    .target(target)
                    .build();

            blockRepository.save(block);
            return "해당 사용자를 차단하였습니다.";
        }
    }
}