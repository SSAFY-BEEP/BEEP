package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.SMSRequestDto;

public interface SMSService {
    String sendExternalMessage(SMSRequestDto.Send send);
    String sendCertMessage(String targetPhone);
    String findPw(String targetPhone);
}
