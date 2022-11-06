package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.SMSRequestDto;

public interface SMSService {
    String sendInviteSMS(SMSRequestDto.Send send);
    String sendCertSMS(String targetPhone);
    String sendTempPwSMS(String targetPhone);
}
