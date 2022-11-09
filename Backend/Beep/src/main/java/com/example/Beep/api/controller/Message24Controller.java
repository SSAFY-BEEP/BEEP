package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.S3RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.Message24ServiceImpl;
import com.example.Beep.api.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Api(value = "2. 24시간메세지(레디스)", tags={"2. 24시간메세지(레디스)"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/message24")
public class Message24Controller {
    private final Message24ServiceImpl service;
    private final BlockService blockService;
    private final S3Service s3Service;

    @ApiOperation(value = "받은 메세지 목록 조회", notes = "해당 회원의 수신메세지 목록 조회 + 차단 거르기")
    @GetMapping("/receive")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getReceiveMessage(){
        return new ResponseEntity<List<Message24>>(service.getReceiveMessage(), HttpStatus.OK);
    }

    @ApiOperation(value = "보낸 메세지 목록 조회", notes = "해당 회원의 발신메세지 목록 조회")
    @GetMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getSendMessage(){
        return new ResponseEntity<List<Message24>>(service.getSendMessage(), HttpStatus.OK);
    }

//    @ApiOperation(value = "메세지 발송/저장", notes = "메세지24 저장(차단시킨 상대일경우 수신에는 저장안함)/차단됐으면 true, 안됐으면 false 리턴")
//    @PostMapping
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> sendMessage(@RequestBody Message24RequestDto.sendMessage message){
//        //receiver가 차단했는지 체크
//        boolean isBlocked = blockService.isBlocked(message.getReceiverNum());
//
//        service.sendMessage(message, isBlocked);
//
//        return new ResponseEntity<String>(isBlocked? "차단된 메세지" : "메세지 정상 발송", HttpStatus.OK);
//    }

    //메세지 파일 들고와서 S3에 저장하면서 DB에도 등록
    @PostMapping(value="/sendFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "메세지 전송(음성파일함께)", notes = "음성메세지와 함께 메세지 전송")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> sendMessageWithFile(@RequestPart(required = false) MultipartFile file ,@RequestPart S3RequestDto.sendMessage24 message24) {
        //S3에 파일 등록
        service.sendMessageWithFile(file, message24);

        return ResponseEntity.ok().body(message24.getContent());
    }

    @ApiOperation(value = "모든 메세지 목록 조회(테스트용)", notes = "모든 회원의 메세지 조회")
    @GetMapping
    public ResponseEntity<?> getAllMessage(){
        return new ResponseEntity<List<Message24>>(service.getAllMessage(), HttpStatus.OK);
    }

    @ApiOperation(value = "id로 메세지 하나 조회", notes = "메세지 id로 메세지 조회")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getMessage(@PathVariable String id){
        return new ResponseEntity<Message24>(service.getMessage(id), HttpStatus.OK);
    }

    @ApiOperation(value = "id로 메세지 삭제", notes = "메세지 id와 해당 데이터 해당 데이터 요청자(토큰)로 메세지 삭제")
    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteMessageById(@RequestParam String messageId){
        //DB에서 메세지 삭제
        service.deleteMessageById(messageId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    //메세지 보관
    @ApiOperation(value = "메세지 보관하기", notes = "message24 id로 해당 요청을 보낸 userid에 해당하는 메세지 데이터 보관(타입 1로 수정)하기")
    @PostMapping("/save/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveMessageById(@PathVariable String messageId){
        //해당 메세지를 메세지(보관)DB에 INSERT & redis type변경
        service.changeMessageType(messageId, MessageType.SAVE.getNum());

        //S3 저장
        s3Service.copyFile(messageId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    //메세지 차단
    @ApiOperation(value = "메세지 차단하기", notes = "message24 id로 상대방 차단하고 해당 메세지도 차단메세지함에 보관")
    @PatchMapping("/block/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> blockMessageById(@PathVariable String messageId){
        //대화관계 차단
        String result = blockService.blockByMsgId(messageId);

        if(result.equals("해당 사용자를 차단하였습니다.")){
            //해당 sender, receiver 대화관계(차단) 설정
            service.changeMessageType(messageId, MessageType.BLOCK.getNum());
        }

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
