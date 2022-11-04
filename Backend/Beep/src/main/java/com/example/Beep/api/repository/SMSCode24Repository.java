package com.example.Beep.api.repository;

import com.example.Beep.api.domain.entity.SMSCode24;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface SMSCode24Repository extends CrudRepository<SMSCode24, String> {

    //해당 번호로 인증번호객체 id조회
    SMSCode24 findByPhoneNumber(String phoneNumber);

    //코드 일치 조회
    boolean existsByPhoneNumberAndCode(String phoneNumber, String code);


}
