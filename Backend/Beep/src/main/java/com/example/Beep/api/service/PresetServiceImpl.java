package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.enums.Authority;
import com.example.Beep.api.domain.entity.Preset;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.PresetRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PresetServiceImpl implements PresetService{


    private final PresetRepository presetRepository;

    private final UserRepository userRepository;

    public void PresetSave(PresetRequestDto presetRequestDto) {
        //토큰의 유저 가져오기
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).orElseThrow(()->new CustomException(ErrorCode.METHOD_NOT_ACCEPTABLE));

        try{
            //프리셋 수정
            Preset now=presetRepository.findPreset(presetRequestDto.getNumber().longValue(),user.getId(), presetRequestDto.getPart()).get();    //없으면 catch
            now.update(presetRequestDto.getNumber(), presetRequestDto.getContent());
            presetRepository.save(now);
        }catch (NoSuchElementException n){
            //프리셋 생성
            Preset preset= Preset.builder()
                    .user(user)
                    .number(presetRequestDto.getNumber())
                    .part(presetRequestDto.getPart())
                    .content(presetRequestDto.getContent())
                    .build();
            presetRepository.save(preset);
        }
    }


    public void PresetDelete(Long id) {
        presetRepository.deleteById(id);
    }

    @Override
    public List<PresetResponseDto> PresetFind(Long id, Integer part) {
        User user;
        if(id==null){
            String ownerNum = SecurityUtil.getCurrentUsername().get();
            user = userRepository.findByPhoneNumber(ownerNum).orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        } else{
            user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        }


        if(user.getAuthority()==Authority.ROLE_LEAVE){
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        }

        List<PresetResponseDto> presetResponseDtoList = user.getPresetList().stream()
                .filter(p -> p.getPart()==part)
                .map(Preset -> PresetResponseDto.builder()
                        .pid(Preset.getId())
                        .uid(user.getId())
                        .number(Preset.getNumber())
                        .part(Preset.getPart())
                        .content(Preset.getContent())
                        .build()).collect(Collectors.toList());
        Collections.sort(presetResponseDtoList, Comparator.comparingInt(PresetResponseDto::getNumber));
        return presetResponseDtoList;
    }
}
