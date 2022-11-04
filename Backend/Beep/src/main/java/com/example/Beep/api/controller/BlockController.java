package com.example.Beep.api.controller;

import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.repository.SMSCode24Repository;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.Message24Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "7. 대화상대 차단", tags={"7. 대화상대 차단"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {
    private final BlockService blockService;
    private final Message24Service message24Service;

    @ApiOperation(value = "차단 목록 전체 조회", notes = "테스트용 차단 목록 전체 조회")
    @GetMapping
    public ResponseEntity<?> getList() {
        return new ResponseEntity<>(blockService.getList(), HttpStatus.OK);
    }

    //메세지24
    @ApiOperation(value = "메세지24에서 유저 차단", notes = "메세지24의 id를 통해서 차단 기능")
    @PostMapping("/24/{messageId}")
    public ResponseEntity<?> blockUser24(@PathVariable("messageId") String messageId) {
        //차단관계 설정
        blockService.blockUser24(messageId);
        //차단 메세지 저장(차단은 2)
        message24Service.changeMessageType(messageId, MessageType.BLOCK.getNum());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    //메세지
    @ApiOperation(value = "메세지에서 유저 차단", notes = "메세지의 id를 통해서 차단 기능")
    @PostMapping("/{messageId}")
    public ResponseEntity<?> blockUser(@PathVariable("messageId") Long messageId) {
        blockService.blockUser(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "유저의 차단 해제", notes = "메세지아이디로 차단 해제 가능")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> blockDelete(@PathVariable("messageId") Long messageId) {
        blockService.blockDelete(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
