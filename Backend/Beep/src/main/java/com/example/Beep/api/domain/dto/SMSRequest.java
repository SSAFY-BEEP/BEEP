package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class SMSRequest {
    @Value("${sms.userid}")
    String userid;           // [필수] 뿌리오 아이디
    @Value("${sms.callback}")
    String callback;    // [필수] 발신번호 - 숫자만
    String phone;   //= "010********";       // [필수] 수신번호 - 여러명일 경우 |로 구분 "010********|010********|010********"
    String msg;     // = "[*이름*] 테스트 발송입니다";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
    String names;   // = "홍길동";            // [선택] 이름 - 여러명일 경우 |로 구분 "홍길동|이순신|김철수"
    String appdate; // = "20190502093000";  // [선택] 예약발송 (현재시간 기준 10분이후 예약가능)
    String subject; // = "테스트";          // [선택] 제목 (30byte)
    String file1;   // = "C:\\test.jpg"    // [선택]  포토발송 (jpg, jpeg만 지원  300 K  이하)

    @Builder
    public SMSRequest(String phone, String msg) {
        this.phone = phone;
        this.msg = msg;
    }

    @Builder
    public SMSRequest(String userid, String callback, String phone, String msg, String names, String appdate, String subject, String file1) {
        this.userid = userid;
        this.callback = callback;
        this.phone = phone;
        this.msg = msg;
        this.names = names;
        this.appdate = appdate;
        this.subject = subject;
        this.file1 = file1;
    }

    @Getter
    public static class Msg {
        String msg;
    }
}
