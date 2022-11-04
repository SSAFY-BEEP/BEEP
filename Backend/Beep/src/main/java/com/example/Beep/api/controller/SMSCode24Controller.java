package com.example.Beep.api.controller;

import com.example.Beep.api.repository.SMSCode24Repository;
import com.example.Beep.api.service.SMSCode24Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "9. 인증번호 발송(테스트용)", tags={"9. 인증번호 발송(테스트용)"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/smscode24")
public class SMSCode24Controller {
    private final SMSCode24Service smsCode24Service;
    private final SMSCode24Repository smsCode24Repository;


    @ApiOperation(value = "문자확인코드 레디스에 저장", notes = "이미 존재하면 데이터 수정, 아니면 데이터 입력(3분 유효기간)")
    @PostMapping
    public ResponseEntity<?> sendCode(@RequestParam String phoneNumber, @RequestParam String code) {
        smsCode24Service.saveCode(phoneNumber, code);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @ApiOperation(value = "문자확인코드 인증", notes = "핸드폰번호와 확인코드가 일치하면 true, 아니면 false")
    @GetMapping("/authorization")
    public ResponseEntity<?> isCorrect(@RequestParam String phoneNumber, @RequestParam String code) {
        return new ResponseEntity<>(smsCode24Service.isCorrect(phoneNumber,code), HttpStatus.OK);
    }

    @ApiOperation(value = "문자확인코드 목록 조회", notes = "모든 문자확인코드 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return new ResponseEntity<>(smsCode24Repository.findAll(),HttpStatus.OK);
    }
}
