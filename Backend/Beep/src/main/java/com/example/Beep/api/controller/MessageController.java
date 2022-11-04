package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "3. 보관/차단 메시지", tags={"3. 보관/차단 메시지"})
@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {

    private final MessageService messageService;

    //토큰으로 조회
    @ApiOperation(value = "토큰으로 보낸 메시지 찾기", notes = "해당 유저 토큰으로 보낸 메세지 조회(보관)")
    @GetMapping("/send")
    public ResponseEntity<?>findSendMessageByToken(){
        return new ResponseEntity<List<MessageResponseDto>>(messageService.findSendMessage(), HttpStatus.OK);
    }

    @ApiOperation(value = "토큰으로 받은 메시지 찾기", notes = "해당 유저 토큰으로 받은 메세지 조회(1=보관, 2=차단)")
    @GetMapping("/recieve/{type}")
    public ResponseEntity<?>findReceiceMessageByToken(@PathVariable Integer type){
        return new ResponseEntity<List<MessageResponseDto>>(messageService.findReceiveMessageByType(type), HttpStatus.OK);
    }

//    @ApiOperation(value = "내가 보낸 메시지 저장", notes = "id는 유저 아이디")
//    @PostMapping("/send")
//    public ResponseEntity<?>saveSendMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
//        messageService.saveSendMessage(persistMessage);
//        return new ResponseEntity<>("Success", HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "내가 받은 메시지 저장", notes = "id는 유저 아이디")
//    @PostMapping("/receive")
//    public ResponseEntity<?>saveReceiveMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
//        messageService.saveReceiveMessage(persistMessage);
//        return new ResponseEntity<>("Success", HttpStatus.OK);
//    }

    @ApiOperation(value = "보관함에서 삭제", notes = "id는 메시지 id")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?>deleteMessage(@PathVariable("messageId") Long messageId){
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "태그 추가,수정", notes = "태그 추가,수정")
    @PatchMapping("/tag")
    public ResponseEntity<?>updateTag(@RequestBody MessageRequestDto.updateTag updateTag){
        messageService.updateTag(updateTag);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}