package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.ApiResult;
import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.dto.UserResponseDto;
import com.example.Beep.api.security.JwtFilter;
import com.example.Beep.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "1. 회원", tags={"1. 회원"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원 가입", notes = "전화번호와 비밀번호, 토큰을 받아서 회원가입")
    @PostMapping("/signup")
    public ApiResult<?> signUp(@RequestBody UserRequestDto.SignUp signUp) {
        UserResponseDto.UserDto user = userService.signUp(signUp);
//        if(user == null) return new ApiResult<String>("Fail", HttpStatus.BAD_REQUEST);
        return new ApiResult<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "관리자의 회원 생성", notes = "정보를 입력해서 유저를 생성함")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<?> createUser(@RequestBody UserRequestDto.CreateUser createUser) {
        userService.createUser(createUser);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "관리자의 회원 정보 수정", notes = "정보를 입력해서 유저정보를 수정함")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResult<?> updateUser(@RequestBody UserRequestDto.CreateUser updateUser, @PathVariable Long id) {
        userService.updateUser(updateUser, id);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "로그인", notes = "전화번호와 비밀번호를 받아서 로그인")
    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody UserRequestDto.Login login) {
        String jwt = userService.login(login);
        //토큰을 헤더에 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        //토큰을 헤더와 바디에 넣어서 리턴해줌
        return new ApiResult<UserResponseDto.Token>(new UserResponseDto.Token(jwt), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 전체 조회(임시Test)", notes = "관리자가 전체 회원 정보를 조회")
    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult getAllUser() {
        return new ApiResult(userService.getAllUser(), HttpStatus.OK);
    }

    @ApiOperation(value = "관리자의 회원 조회", notes = "회원 전화번호를 통해 정보 조회")
    @GetMapping("/{phone}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<?> getDetail(@PathVariable String phone) {
        return new ApiResult<UserResponseDto.UserDto>(userService.getUser(phone), HttpStatus.OK);
    }
    @ApiOperation(value = "유저의 정보 조회", notes = "토큰을 통해서 자신의 정보 조회")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResult<?> getMyInfo() {
        return new ApiResult<UserResponseDto.UserDto>(userService.getMyUserWithAuth(), HttpStatus.OK);
    }
    @ApiOperation(value = "유저 회원 탈퇴", notes = "토큰을 통해서 유저 회원 탈퇴")
    @PatchMapping("/withdrawal")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> withdrawal() {
        userService.withdrawal();
        return new ApiResult<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "관리자의 회원 탈퇴", notes = "사용자의 전화번호를 통해서 관리자가 회원을 탈퇴시킴")
    @PatchMapping("/withdrawal/{phone}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<?> withdrawalByAdmin(@PathVariable String phone) {
        userService.withdrawal(phone);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 원하는대로 변경해줌")
    @PatchMapping("/pw")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> changePassword(@RequestBody UserRequestDto.Login newPw) {
        String result = userService.changePassword(newPw);
        return new ApiResult<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "소리/알림음 설정", notes = "유저가 자신의 삐삐 알림음을 설정")
    @PatchMapping("/alarm/{number}")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> changeAlarm(@PathVariable Integer number) {
        userService.changeAlarm(number);
        return new ApiResult<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "폰트 설정", notes = "유저가 자신의 삐삐 UI의 폰트를 설정")
    @PatchMapping("/font/{number}")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> changeFont(@PathVariable Integer number) {
        userService.changeFont(number);
        return new ApiResult<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "배경 테마 설정", notes = "유저가 자신의 삐삐 배경 테마를 설정")
    @PatchMapping("/theme/{number}")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> changeTheme(@PathVariable Integer number) {
        userService.changeTheme(number);
        return new ApiResult<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "각인 설정", notes = "유저가 자신의 삐삐에 각인을 설정")
    @PatchMapping("/engrave/{engrave}")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> changeEngrave(@PathVariable String engrave) {
        userService.changeEngrave(engrave);
        return new ApiResult<>("Success", HttpStatus.OK);
    }
}