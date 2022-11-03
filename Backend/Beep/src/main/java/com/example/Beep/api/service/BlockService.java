package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.dto.UserRequestDto;

import java.util.List;

public interface BlockService {
    List<BlockResponseDto> getList();

    boolean isBlocked(String userNum, String targetNum);

    void blockUser(Long id);

    void blockDelete(Long id);
}

