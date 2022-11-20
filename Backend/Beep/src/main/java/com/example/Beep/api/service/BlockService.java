package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.BlockResponseDto;
import com.example.Beep.api.domain.dto.UserRequestDto;

import java.util.List;

public interface BlockService {
    List<BlockResponseDto> getList();

    boolean isBlocked(String targetNum);

    String blockUser(Long messageId);

    //메세지24에서 해당 메세지로 차단관계 생성
    String blockByMsgId(String message24Id);

    void blockDelete(Long id);

}

