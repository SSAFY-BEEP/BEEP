package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.Message24Dto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.service.Message24ServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "레디스 24시간 메세지", tags={"레디스 메세지"})
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/redis24")
public class Message24Controller {
    private final Message24ServiceImpl service;

    @ApiOperation(value = "받은 메세지 목록 조회", notes = "해당 회원의 수신메세지 목록 조회")
    @GetMapping("/receive/{receiver}")
    public ResponseEntity<?> getReceiveMessage(@PathVariable Long receiver){
        return new ResponseEntity<List<Message24>>(service.getReceiveMessage(receiver), HttpStatus.OK);
    }

    @ApiOperation(value = "보낸 메세지 목록 조회", notes = "해당 회원의 발신메세지 목록 조회")
    @GetMapping("/send/{sender}")
    public ResponseEntity<?> getSendMessage(@PathVariable Long sender){
        return new ResponseEntity<List<Message24>>(service.getSendMessage(sender), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 발송/저장", notes = "메세지 발송 시, 레디스에 메세지 저장")
    @PostMapping
    public ResponseEntity<?> saveMessage(@RequestBody Message24Dto.sendMessage message){
        service.saveMessage(message);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "모든 메세지 목록 조회(테스트용)", notes = "모든 회원의 메세지 조회")
    @GetMapping
    public ResponseEntity<?> getAllMessage(){
        return new ResponseEntity<List<Message24>>(service.getAllMessage(), HttpStatus.OK);
    }

    @ApiOperation(value = "id로 메세지 하나 조회", notes = "id로 메세지 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable String id){
        return new ResponseEntity<Message24>(service.getMessage(id), HttpStatus.OK);
    }

    @ApiOperation(value = "id로 메세지 삭제", notes = "idx로 메세지 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessageByIdx(@PathVariable String id){
        service.deleteMessageById(id);
        return ResponseEntity.ok().build();
    }

    //메세지 차단

}
