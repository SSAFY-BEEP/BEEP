package com.example.Beep.api.service;


import com.example.Beep.api.domain.entity.Message24;

import java.util.List;

public interface Message24Service {
    //받은 메세지 조회
    List<Message24> getReceiveMessage(String receiver);

    //보낸 메세지 조회
    List<Message24> getSendMessage(String sender);

    //해당 메세지 조회

    //메세지 발송(저장)
    void saveMessage(Message24 message);

    //메세지 보관

    //
}
