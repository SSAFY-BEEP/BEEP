package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.service.MessageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "3. 메시지 영구 보관", tags={"3. 메시지 영구 보관"})
@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/save")
    public ResponseEntity<?>saveMessage(@RequestBody MessageRequestDto.sendMessage sendMessage){
        messageService.saveMessage(sendMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteMessage(@RequestBody MessageRequestDto.sendMessage sendMessage){
        messageService.deleteMessage(sendMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
