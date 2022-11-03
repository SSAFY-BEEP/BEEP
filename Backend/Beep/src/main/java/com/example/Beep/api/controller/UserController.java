package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.dto.UserResponseDto;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.security.JwtFilter;
import com.example.Beep.api.service.BlockService;
import com.example.Beep.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "1. 회원 관리", tags={"1. 회원 관리"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BlockService blockService;

    @ApiOperation(value = "회원 가입", notes = "전화번호와 비밀번호, 토큰을 받아서 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto.SignUp signUp) {
        User user = userService.signUp(signUp);
//        if(user == null) return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @ApiOperation(value = "로그인", notes = "전화번호와 비밀번호를 받아서 로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto.Login login) {
        String jwt = userService.login(login);
        //토큰을 헤더에 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        //토큰을 헤더와 바디에 넣어서 리턴해줌
        return new ResponseEntity<>(new UserResponseDto.Token(jwt), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 전체 조회", notes = "관리자가 전체 회원 정보를 조회")
    @GetMapping("/all")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<List<User>>(userService.getAllUser(), HttpStatus.OK);
    }

    @ApiOperation(value = "관리자의 회원 조회", notes = "회원 전화번호를 통해 정보 조회")
    @GetMapping("/{phone}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getDetail(@PathVariable String phone) {
        return new ResponseEntity<User>(userService.getUser(phone), HttpStatus.OK);
    }
    @ApiOperation(value = "유저의 정보 조회", notes = "토큰을 통해서 자신의 정보 조회")
    @GetMapping
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getMyInfo() {
        return new ResponseEntity<User>(userService.getMyUserWithAuth().get(), HttpStatus.OK);
    }
    @ApiOperation(value = "유저 회원 탈퇴", notes = "토큰을 통해서 유저 회원 탈퇴")
    @PatchMapping
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> withdrawal() {
        userService.withdrawal();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "관리자의 회원 탈퇴", notes = "사용자의 전화번호를 통해서 관리자가 회원을 탈퇴시킴")
    @PatchMapping("/{phone}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> withdrawalByAdmin(@PathVariable String phone) {
        userService.withdrawal(phone);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호를 찾고 바꾼 메시지를 리턴해줌")
    @GetMapping("/findPw/{phone}")
    public ResponseEntity<?> findPassword(@PathVariable String phone) {
        String result = userService.findPassword(phone);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호를 원하는대로 변경해줌")
    @PatchMapping("/pw")
    public ResponseEntity<?> changePassword(@RequestBody UserRequestDto.Login newPw) {
        String result = userService.changePassword(newPw);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "소리/알림음 설정", notes = "유저가 자신의 삐삐 알림음을 설정")
    @PatchMapping("/alarm/{number}")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> changeAlarm(@PathVariable Integer number) {
        userService.changeAlarm(number);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "폰트 설정", notes = "유저가 자신의 삐삐 UI의 폰트를 설정")
    @PatchMapping("/font/{number}")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> changeFont(@PathVariable Integer number) {
        userService.changeFont(number);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "배경 테마 설정", notes = "유저가 자신의 삐삐 배경 테마를 설정")
    @PatchMapping("/theme/{number}")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> changeTheme(@PathVariable Integer number) {
        userService.changeTheme(number);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @ApiOperation(value = "각인 설정", notes = "유저가 자신의 삐삐에 각인을 설정")
    @PatchMapping("/engrave/{engrave}")
//    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> changeEngrave(@PathVariable String engrave) {
        userService.changeEngrave(engrave);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}