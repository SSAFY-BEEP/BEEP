package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.service.Message24ServiceImpl;
import com.example.Beep.api.service.Relation24ServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "2. 레디스 24시간 메세지", tags={"2. 레디스 메세지"})
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/message24")
public class Message24Controller {
    private final Message24ServiceImpl service;
    private final Relation24ServiceImpl relation24Service;

    @ApiOperation(value = "받은 메세지 목록 조회", notes = "해당 회원의 수신메세지 목록 조회 + 차단 거르기")
    @GetMapping("/receive/{receiver}")
    public ResponseEntity<?> getReceiveMessage(@PathVariable Long receiver){
//        return new ResponseEntity<String>("22",HttpStatus.OK);
        return new ResponseEntity<List<Message24>>(service.getReceiveMessage(receiver), HttpStatus.OK);
    }

    @ApiOperation(value = "보낸 메세지 목록 조회", notes = "해당 회원의 발신메세지 목록 조회")
    @GetMapping("/send/{sender}")
    public ResponseEntity<?> getSendMessage(@PathVariable Long sender){
        return new ResponseEntity<List<Message24>>(service.getSendMessage(sender), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 발송/저장", notes = "메세지 발송 시, 레디스에 메세지 저장")
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message24RequestDto.sendMessage message){
        service.sendMessage(message);

        //대화관계 시간 갱신or저장
        relation24Service.insertRelation(message);

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
    public ResponseEntity<?> deleteMessageById(@PathVariable String id){
        service.deleteMessageById(id);
        return ResponseEntity.ok().build();
    }

    //메세지 보관
    @ApiOperation(value = "메세지 보관하기", notes = "message24 id로 메세지 보관하기")
    @PostMapping("/save/{id}")
    public ResponseEntity<?> saveMessageById(@PathVariable String id){
        //해당 메세지를 메세지(보관)DB에 INSERT
        service.saveMessage(id);

        return ResponseEntity.ok().build();
    }

    //보관 메세지 목록


    //메세지 차단
    @ApiOperation(value = "메세지 차단하기", notes = "message24 id로 메세지 차단하기")
    @PatchMapping("/block")
    public ResponseEntity<?> blockMessageById(@RequestBody String id){
        //해당 메세지를 메세지(차단)DB에 INSERT

        //해당 sender, receiver 대화관계(차단) 설정

        return ResponseEntity.ok().build();
    }

    //차단 메세지 목록

    //차단 풀기기
}
