package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.entity.Authority;
import com.example.Beep.api.domain.entity.Preset;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.PresetRepository;
import com.example.Beep.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PresetServiceImpl implements PresetService{


    @Autowired
    PresetRepository presetRepository;

    @Autowired
    UserRepository userRepository;

    public void PresetSave(PresetRequestDto presetRequestDto) {
        try{
            Preset now=presetRepository.findPreset(presetRequestDto.getNumber().longValue(),presetRequestDto.getUid().longValue()).get();
            now.update(presetRequestDto.getNumber(), presetRequestDto.getContent());
            presetRepository.save(now);
        }catch (NoSuchElementException n){
            Preset preset= Preset.builder()
                    .user(userRepository.findById(presetRequestDto.getUid()).get())
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
    public List<PresetResponseDto> PresetFind(Long id) {
        List<PresetResponseDto> presetResponseDtoList = new ArrayList<>();
        try {
            User user = userRepository.findById(id).get();
            if(user.getAuthority().equals(Authority.ROLE_LEAVE)){
                return null;
            }
            //for문 말고 그냥 처리하는 방법 아시는분???
            List<Preset> presetList = user.getPresetList();
            for (Preset preset : presetList) {
                PresetResponseDto presetResponseDto = PresetResponseDto.builder()
                        .uid(id)
                        .number(preset.getNumber())
                        .part(preset.getPart())
                        .content(preset.getContent())
                        .build();
                presetResponseDtoList.add(presetResponseDto);
            }
        }catch (NoSuchElementException n){
            System.out.println("없는 유저 입니다");
        }
        return presetResponseDtoList;
    }
}
