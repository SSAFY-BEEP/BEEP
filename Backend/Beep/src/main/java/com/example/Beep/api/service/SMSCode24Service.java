package com.example.Beep.api.service;

import com.example.Beep.api.domain.entity.SMSCode24;

public interface SMSCode24Service {
    //인증번호 수정/저장
    void saveCode(String phoneNumber,String code);

    //인증암호 일치여부
    boolean isCorrect(String phoneNumber,String code);
}
