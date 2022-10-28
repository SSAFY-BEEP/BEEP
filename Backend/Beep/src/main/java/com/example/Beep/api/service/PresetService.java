package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;

import java.util.List;

public interface PresetService {

    void PresetSave(PresetRequestDto presetRequestDto);

    void PresetDelete(Long id);

    List<PresetResponseDto> PresetFind(Long id);
}
