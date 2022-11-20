package com.example.Beep.api.service;

import com.example.Beep.api.domain.entity.SMSCode24;
import com.example.Beep.api.repository.SMSCode24Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class SMSCode24ServiceImpl implements SMSCode24Service {
    
    private final SMSCode24Repository repository;
    
    //암호 저장
    @Override
    public void saveCode(String phoneNumber,String code) {
        SMSCode24 smsCode24 = repository.findByPhoneNumber(phoneNumber);
        //해당 핸드폰번호로 보낸 인증문자가 있으면
        if(smsCode24 != null){  //해당 데이터 업데이트
            smsCode24 = SMSCode24.builder()
                    .id(smsCode24.getId())
                    .phoneNumber(phoneNumber)
                    .code(code)
                    .build();
        } else{                 //새로 만들기
            smsCode24 = SMSCode24.builder()
                    .phoneNumber(phoneNumber)
                    .code(code)
                    .build();
        }

        repository.save(smsCode24);
    }

    @Override
    public boolean isCorrect(String phoneNumber, String code) {
        return repository.existsByPhoneNumberAndCode(phoneNumber,code);
    }
}
