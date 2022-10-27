package com.example.Beep.api.service;

import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.repository.Message24Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Message24ServiceImpl implements  Message24Service{
    private final Message24Repository repository;

    @Override
    public List<Message24> getReceiveMessage(String receiver) {
        return repository.findAllByReceiver(receiver);
    }

    @Override
    public List<Message24> getSendMessage(String sender) {
        return repository.findAllBySender(sender);
    }

    @Transactional
    @Override
    public void saveMessage(Message24 message) {
        repository.save(message);
    }
}
