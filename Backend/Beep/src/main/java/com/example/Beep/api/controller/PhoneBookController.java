package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.PhoneBookRequestDto;
import com.example.Beep.api.service.PhoneBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "8. 연락처 연동", tags = {"8. 연락처"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/phonebook")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;

    @ApiOperation(value = "유저 연락처 연동(등록)", notes = "연락처를 리스트로 받아서 디비에 등록")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> registerPhoneBook(@RequestBody List<PhoneBookRequestDto.Register> registerList) {
        phoneBookService.registerPhone(registerList);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "등록된 연락처 조회", notes = "유저가 연동한 연락처들을 조회")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> getPhoneBookByUser() {
        return new ResponseEntity<>(phoneBookService.getPhoneList(), HttpStatus.OK);
    }
}
