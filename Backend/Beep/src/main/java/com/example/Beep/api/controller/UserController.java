package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.dto.UserResponseDto;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.security.JwtFilter;
import com.example.Beep.api.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Api(value = "회원 관리", tags={"회원 관리"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto.SignUp signUp) {
        User user = userService.signUp(signUp);
//        if(user == null) return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto.Login login) {
        String jwt = userService.login(login);
        //토큰을 헤더에 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        //토큰을 헤더와 바디에 넣어서 리턴해줌
        return new ResponseEntity<>(new UserResponseDto.Token(jwt), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getDetail(@PathVariable String phone) {
        return new ResponseEntity<User>(userService.getUser(phone), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getMyInfo() {
        return new ResponseEntity<User>(userService.getMyUserWithAuth().get(), HttpStatus.OK);
    }
    @PatchMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<?> withdrawal() {
        userService.withdrawal();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @PatchMapping("/{phone}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> withdrawalByAdmin(@PathVariable String phone) {
        userService.withdrawal(phone);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}