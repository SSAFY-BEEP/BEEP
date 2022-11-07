package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.MessageRequestDto;
import com.example.Beep.api.domain.dto.MessageResponseDto;
import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "3. 메시지(보관/차단)", tags={"3.메시지(보관/차단)"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final BlockService blockService;

    @ApiOperation(value = "전체 메세지 조회(테스트)", notes = "전체 메세지 조회")
    @GetMapping
    public ResponseEntity<?>findAll(){
        return new ResponseEntity<>(messageService.getAll(), HttpStatus.OK);
    }

    //토큰으로 조회
    @ApiOperation(value = "토큰으로 보낸 메시지 찾기", notes = "해당 유저 토큰으로 보낸 메세지 조회(보관)")
    @GetMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>findSendMessageByToken(){
        return new ResponseEntity<List<MessageResponseDto>>(messageService.findSendMessage(), HttpStatus.OK);
    }

    @ApiOperation(value = "토큰으로 받은 메시지 찾기", notes = "해당 유저 토큰으로 받은 메세지 조회(1=보관, 2=차단)")
    @GetMapping("/recieve/{type}")
    @PreAuthorize("hasRole('USER')")
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

    @ApiOperation(value = "메세지 보관", notes = "메세지의 id를 통해서 보관 기능")
    @PostMapping("/save/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveMessage(@PathVariable("messageId") Long messageId) {
        String result = messageService.changeMessageType(messageId, MessageType.SAVE.getNum());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "메세지 차단", notes = "메세지의 id를 통해서 차단 기능")
    @PostMapping("/block/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> blockUser(@PathVariable("messageId") Long messageId) {
        String result = blockService.blockUser(messageId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "보관함에서 삭제", notes = "message id로 삭제")
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>deleteMessage(@PathVariable Long messageId){
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "태그 추가,수정", notes = "태그 추가,수정")
    @PatchMapping("/tag")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>updateTag(@RequestBody MessageRequestDto.updateTag updateTag){
        messageService.updateTag(updateTag);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}