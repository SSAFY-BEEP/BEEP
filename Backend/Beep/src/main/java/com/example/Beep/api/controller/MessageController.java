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

@Api(value = "3. 메시지 영구 보관", tags={"3. 메시지 영구 보관"})
@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {

    private final MessageService messageService;

    @ApiOperation(value = "내가 보낸 메시지 찾기, id는 유저 아이디", notes = "내가 보낸 메시지 찾기, id는 유저 아이디")
    @GetMapping("/findsend/{userId}")
    public ResponseEntity<?>findSendMessage(@PathVariable("userId") Long userId){
        List<MessageResponseDto>messageResponseDtoList=messageService.findSendMessage(userId);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 받은 메시지 찾기, id는 유저 아이디", notes = "내가 받은 메시지 찾기, id는 유저 아이디")
    @GetMapping("/findrecieve/{userId}")
    public ResponseEntity<?>findReceiceMessage(@PathVariable("userId") Long userId){
        List<MessageResponseDto>messageResponseDtoList=messageService.findReceiveMessage(userId);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 보낸 메시지 저장, id는 유저 아이디", notes = "내가 보낸 메시지 저장, id는 유저 아이디")
    @PostMapping("/send")
    public ResponseEntity<?>saveSendMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveSendMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "내가 받은 메시지 저장", notes = "내가 받은 메시지 저장")
    @PostMapping("/receive")
    public ResponseEntity<?>saveReceiveMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveReceiveMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "보관함에서 삭제,id는 메시지 id입니다", notes = "보관함에서 삭제,id는 메시지 id입니다")
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
