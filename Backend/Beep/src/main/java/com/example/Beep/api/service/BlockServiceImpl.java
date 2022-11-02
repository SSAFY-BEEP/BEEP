package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
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
}
