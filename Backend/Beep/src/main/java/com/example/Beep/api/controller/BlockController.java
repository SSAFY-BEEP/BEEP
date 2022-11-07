package com.example.Beep.api.controller;

import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.Message24Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(value = "4. 메세지 차단(테스트용)", tags={"4. 메세지 차단(테스트용)"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/block")
public class BlockController {
    private final BlockService blockService;
    private final Message24Service message24Service;

    @ApiOperation(value = "차단 목록 전체 조회(TEST)", notes = "테스트용 차단 목록 전체 조회")
    @GetMapping
    public ResponseEntity<?> getList() {
        return new ResponseEntity<>(blockService.getList(), HttpStatus.OK);
    }

    //메세지24에 존재
//    @ApiOperation(value = "메세지24에서 유저 차단", notes = "메세지24의 id를 통해서 차단 기능")
//    @PostMapping("/24/{messageId}")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<?> blockUser24(@PathVariable("messageId") String messageId) {
//        //레디스 메세지 차단으로 저장(차단은 2)
//        Long result = message24Service.changeMessageType(messageId, MessageType.BLOCK.getNum());
//
//        //차단관계 설정
//        blockService.blockUser(result);
//
//        return new ResponseEntity<>("Success", HttpStatus.OK);
//    }

    //메세지
    @ApiOperation(value = "메세지에서 유저 차단", notes = "메세지의 id를 통해서 차단 기능")
    @PostMapping("/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> blockUser(@PathVariable("messageId") Long messageId) {
        blockService.blockUser(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "유저의 차단 해제", notes = "메세지아이디로 차단 해제 가능")
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> blockDelete(@PathVariable("messageId") Long messageId) {
        blockService.blockDelete(messageId);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
