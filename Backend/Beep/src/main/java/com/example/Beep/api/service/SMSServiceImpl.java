package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.SMSRequestDto;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${sms.api_key}")
    private String api_key;
    @Value("${sms.api_secret}")
    private String api_secret;
    @Value("${sms.from}")
    private String api_from;

    //메시지 발송 메서드
    public String sendSMS(String targetPhone, String msg) {
        Message coolSms = new Message(api_key, api_secret);
        /*
         * Parameters
         * 관련정보 : http://www.coolsms.co.kr/SDK_Java_API_Reference_ko#toc-0
         */
        //msg 길이가 80보다 적으면 sms타입으로 길면 lms타입으로
        String type = msg.getBytes().length<80? "sms" : "lms";

        HashMap<String, String> set = new HashMap<String, String>();
        set.put("to", targetPhone); // 수신번호
        set.put("from", api_from); // 발신번호
        set.put("text", msg); // 문자내용
        set.put("type", type); // 문자 타입

        try {
            JSONObject result = coolSms.send(set); // 보내기&전송결과받기
            System.out.println(result.toString());
            return "Success "+type;
        } catch (CoolsmsException e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    @Override
    public String sendInviteSMS(String targetNum) {
        //초대 메시지 작성 필요!
        String msg ="[BEEP]\n";
        msg+= "삡-\n";
        msg+= "누군가 당신에게 삐삐메세지를 보냈어요.\n";
        msg+= "24시간이 지나면 사라져요!";
        msg+= "\n\n";
        msg+= "받은 비밀메세지가 궁금하다면?";
        msg+= "초성으로 소통하는 삐삐앱 BEEP으로!\n";
        //임시링크
        msg+= "https://play.google.com/store/apps/details?id=com.tenbil.beeper";
        return sendSMS(targetNum, msg);
//        return "Success";
    }

    @Override
    public String sendCertSMS(String targetPhone) {
        //6자리 난수
        int certNum = new Random(System.currentTimeMillis()).nextInt(999999);
        String msg ="[BEEP] 인증코드 :" + Integer.toString(certNum);
        String result = sendSMS(targetPhone, msg);

        //인증 번호를 리턴
        return result.equals("Success") ? Integer.toString(certNum) : result;
    }

    @Override
    public String sendTempPwSMS(String targetPhone) {
        //난수로 12자리 임시 비밀번호 생성
        String newPw = getRamdomPassword(12);
        //유저가 없으면 오류
        User user = userRepository.findByPhoneNumber(targetPhone).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        user.changePw(passwordEncoder.encode(newPw));
        //메시지 내용
        String msg ="[BEEP]임시비밀번호 : " + newPw;
        String result = sendSMS(targetPhone, msg);
        //메시지가 정상적으로 가야 비밀번호를 바꿔줌
        if(result.equals("Success")) {
            userRepository.save(user);
            return msg;
        } else return result;
    }

    public String getRamdomPassword(int size) {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'};

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) {
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}
