package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
import com.example.Beep.api.repository.Message24Repository;
import com.example.Beep.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {
    private final BlockRepository repository;
    private final UserRepository userRepository;

    private final Message24Repository message24Repository;

    @Override
    public List<BlockResponseDto> getList() {
        List<Block> list = repository.findAll();

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

        return repository.existsByUserAndTarget(findUser, findTarget);
    }

    @Override
    @Transactional
    public void blockUser(UserRequestDto.Block block) {
        try{
            User user=userRepository.findById(block.getUserId()).get();
            User target=userRepository.findById(block.getTargetId()).get();

            if(repository.existsByUserAndTarget(user,target)){
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }

            Block newBlock= Block.builder()
                    .user(user)
                    .target(target)
                    .build();
            repository.save(newBlock);
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void blockDelete(UserRequestDto.Block block) {
        try{
            User user=userRepository.findById(block.getUserId()).get();
            User target=userRepository.findById(block.getTargetId()).get();

            repository.deleteByUserAndTarget(user,target);
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    @Override
    public String blockByMsgId(String message24Id) {
        Message24 message24 = message24Repository.findById(message24Id).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));

        User user = userRepository.findByPhoneNumber(message24.getOwnerNum()).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User target = userRepository.findByPhoneNumber(message24.getSenderNum()).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));

        //존재하는 차단관계인지 확인
        if(repository.existsByUserAndTarget(user, target)){ //이미 존재
            return "이미 차단된 사용자입니다.";
        } else{ //안 존재
            //차단 관계 설정
            Block block = Block.builder()
                    .user(user)
                    .target(target)
                    .build();

            repository.save(block);
            return "해당 사용자를 차단하였습니다.";
        }
    }
}
