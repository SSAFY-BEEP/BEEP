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

    @ApiOperation(value = "내가 보낸 메시지 찾기", notes = "내가 보낸 메시지 찾기")
    @GetMapping("/findsend/{id}")
    public ResponseEntity<?>findSendMessage(@PathVariable("id") Long id){
        List<MessageResponseDto>messageResponseDtoList=messageService.findSendMessage(id);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 받은 메시지 찾기, id는 유저 아이디", notes = "내가 받은 메시지 찾기, id는 유저 아이디")
    @GetMapping("/findrecieve/{id}")
    public ResponseEntity<?>findReceiceMessage(@PathVariable("id") Long id){
        List<MessageResponseDto>messageResponseDtoList=messageService.findReceiveMessage(id);
        return new ResponseEntity<List<MessageResponseDto>>(messageResponseDtoList, HttpStatus.OK);
    }

    @ApiOperation(value = "내가 보낸 메시지 저장, id는 유저 아이디", notes = "내가 보낸 메시지 저장, id는 유저 아이디")
    @PostMapping("/save/send")
    public ResponseEntity<?>saveSendMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveSendMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "내가 받은 메시지 저장", notes = "내가 받은 메시지 저장")
    @PostMapping("/save/receive")
    public ResponseEntity<?>saveReceiveMessage(@RequestBody MessageRequestDto.persistMessage persistMessage){
        messageService.saveReceiveMessage(persistMessage);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "보관함에서 삭제,id는 메시지 id입니다", notes = "보관함에서 삭제,id는 메시지 id입니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteMessage(@PathVariable("id") Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "태그 추가", notes = "태그 추가")
    @PatchMapping("/tag")
    public ResponseEntity<?>updateTag(@RequestBody MessageRequestDto.updateTag updateTag){
        messageService.updateTag(updateTag);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
