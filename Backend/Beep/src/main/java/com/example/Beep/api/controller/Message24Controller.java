package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.Message24RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.security.SecurityUtil;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.Message24ServiceImpl;
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
//    private final Relation24ServiceImpl relation24Service;
    private final BlockService blockService;

    @ApiOperation(value = "받은 메세지 목록 조회", notes = "해당 회원의 수신메세지 목록 조회 + 차단 거르기")
    @GetMapping("/receive/{receiver}")
    public ResponseEntity<?> getReceiveMessage(@PathVariable String receiverNum){
//        return new ResponseEntity<String>("22",HttpStatus.OK);
        return new ResponseEntity<List<Message24>>(service.getReceiveMessage(receiverNum), HttpStatus.OK);
    }

    @ApiOperation(value = "보낸 메세지 목록 조회", notes = "해당 회원의 발신메세지 목록 조회")
    @GetMapping("/send/{sender}")
    public ResponseEntity<?> getSendMessage(@PathVariable String senderNum){
        return new ResponseEntity<List<Message24>>(service.getSendMessage(senderNum), HttpStatus.OK);
    }

    @ApiOperation(value = "메세지 발송/저장", notes = "메세지 발송 시, 레디스에 메세지 저장(차단시킨 상대일경우 저장안함)")
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message24RequestDto.sendMessage message){
        //receiver가 차단했는지 체크
        String answer = "";
        if(!blockService.isBlocked(message.getReceiverNum(), message.getSenderNum())){    //차단 안됐으면
            service.sendMessage(message);
            //대화관계 시간 갱신or저장 -> 사라진 기능
//          relation24Service.insertRelation(message);
            answer = "메세지 전송 완료";
        } else{
            answer = "차단된 메세지";
        }
        return new ResponseEntity<String>(answer, HttpStatus.OK);
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

    @ApiOperation(value = "id로 메세지 삭제", notes = "메세지id와 해당 데이터ownerId(요청자)로 메세지 삭제")
    @DeleteMapping
    public ResponseEntity<?> deleteMessageById(@RequestParam String id,@RequestParam String ownerNum){
        service.deleteMessageById(id, ownerNum);
        return ResponseEntity.ok().build();
    }

    //메세지 보관
    @ApiOperation(value = "메세지 보관하기", notes = "message24 id로 해당 요청을 보낸 userid에 해당하는 메세지 데이터 보관(타입 1로 수정)하기")
    @PostMapping("/save/{id}")
    public ResponseEntity<?> saveMessageById(@RequestBody Message24RequestDto.changeMessage message){
        //해당 메세지를 메세지(보관)DB에 INSERT & redis type변경
        service.changeMessageType(message.getId(), 1);

        return ResponseEntity.ok().build();
    }

    //메세지 차단
    @ApiOperation(value = "메세지 차단하기", notes = "message24 id로 상대방 차단하고 해당 메세지도 차단메세지함에 보관")
    @PatchMapping("/block")
    public ResponseEntity<?> blockMessageById(@RequestParam String id){
        //대화관계 차단
        blockService.blockByMsgId(id);

        //해당 sender, receiver 대화관계(차단) 설정
        service.changeMessageType(id, 2);

        return ResponseEntity.ok().build();
    }
}
