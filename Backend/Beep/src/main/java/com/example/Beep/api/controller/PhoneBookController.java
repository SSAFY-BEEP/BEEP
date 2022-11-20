package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.ApiResult;
import com.example.Beep.api.domain.dto.PhoneBookRequestDto;
import com.example.Beep.api.service.PhoneBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "6. 연락처", tags = {"6. 연락처"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/phonebook")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;

    @ApiOperation(value = "유저 연락처 연동(등록)", notes = "연락처를 리스트로 받아서 디비에 등록")
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> registerPhoneBook(@RequestBody List<PhoneBookRequestDto.Register> registerList) {
        phoneBookService.registerPhone(registerList);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "등록된 연락처 조회", notes = "유저가 연동한 연락처들을 조회")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> getPhoneBookByUser() {
        return new ApiResult<>(phoneBookService.getPhoneList(), HttpStatus.OK);
    }

    @ApiOperation(value = "등록된 연락처 수정", notes = "유저가 연동한 연락처 하나를 수정")
    @PatchMapping("/{phone}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> updatePhoneBookByUser(@PathVariable String phone, @RequestBody PhoneBookRequestDto.Register update) {
        return new ApiResult<>(phoneBookService.updatePhone(phone, update), HttpStatus.OK);
    }

    @ApiOperation(value = "등록된 연락처 삭제", notes = "유저가 연동한 연락처 하나를 삭제")
    @DeleteMapping("/{phone}")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> deletePhoneBookByUser(@PathVariable String phone) {
        phoneBookService.deletePhone(phone);
        return new ApiResult<>("Success", HttpStatus.OK);
    }
}
