package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.Relation24RequestDto;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.Relation24;
import com.example.Beep.api.service.Relation24Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;import java.util.List;

@Api(value = "24시간 대화관계(레디스)", tags={"대화관계"})
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/relation24")
public class RelationController {
    private final Relation24Service service;

    @ApiOperation(value = "대화관계 전체 조회(레디스)", notes = "대화관계 전체조회")
    @GetMapping
    public ResponseEntity<?> getList(){
        return new ResponseEntity<List<Relation24>>(service.getList(), HttpStatus.OK);
    }

    @ApiOperation(value = "대화관계 조건으로 조회", notes = "대화관계 sender,receiver로 필터링조회")
    @GetMapping("/filter")
    public ResponseEntity<?> findById(@RequestParam Long receiverId, @RequestParam Long senderId){
        return new ResponseEntity<Relation24>(service.findBySenderAndReceiver(receiverId,senderId), HttpStatus.OK);
    }

}
