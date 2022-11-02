package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "7. 차단", tags={"7. 차단"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/block")
public class BlockController {

    private final BlockService blockService;

    @ApiOperation(value = "차단 목록 전체 조회", notes = "테스트용 차단 목록 전체 조회")
    @GetMapping
    public ResponseEntity<?> getList() {
        return new ResponseEntity<>(blockService.getList(), HttpStatus.OK);
    }

    @ApiOperation(value = "유저 차단", notes = "id를 통해서 차단 기능")
    @PostMapping
    public ResponseEntity<?> blockUser(@RequestBody UserRequestDto.Block block) {
        blockService.blockUser(block);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "유저의 차단 해제", notes = "사용자의 전화번호를 통해서 차단 해제 가능")
    @DeleteMapping
    public ResponseEntity<?> blockDelete(@RequestBody UserRequestDto.Block block) {
        blockService.blockDelete(block);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
