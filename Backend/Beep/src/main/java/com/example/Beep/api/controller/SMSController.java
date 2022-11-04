package com.example.Beep.api.controller;

import com.example.Beep.api.service.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "9. SMS", tags = {"9. SMS"})
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SMSController {

    private final SMSService smsService;

    @ApiOperation(value = "휴대폰 인증 메시지 발송", notes = "받은 휴대폰 번호로 6자리 난수를 발송하고 프론트에도 리턴")
    @GetMapping("/cert/{phone}")
    public ResponseEntity<?> sendCertSMS(@PathVariable String phone){
        return new ResponseEntity<>(smsService.sendCertMessage(phone), HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호를 찾고 바꾼 비밀번호를 리턴해줌")
    @GetMapping("/findPw/{phone}")
    public ResponseEntity<?> findPassword(@PathVariable String phone) {
        String result = smsService.findPw(phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
