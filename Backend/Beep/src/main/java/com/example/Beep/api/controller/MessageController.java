package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
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

    @GetMapping("/findsend/{id}")
    public ResponseEntity<?>findSendMessage(@PathVariable("id") Long id){
        List<MessageResponseDto>messageResponseDtoList=messageService.findSendMessage(id);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/findrecieve/{id}")
    public ResponseEntity<?>findReceiceMessage(@PathVariable("id") Long id){
        List<MessageResponseDto>messageResponseDtoList=messageService.findReceiveMessage(id);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/save/send")
    public ResponseEntity<?>saveSendMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveSendMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/save/receive")
    public ResponseEntity<?>saveReceiveMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveReceiveMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteMessage(@PathVariable("id") Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PatchMapping("/tag")
    public ResponseEntity<?>updateTag(@RequestBody MessageRequestDto.updateTag updateTag){
        messageService.updateTag(updateTag);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
