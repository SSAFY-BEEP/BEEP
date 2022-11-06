package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.dto.UserResponseDto;
import com.example.Beep.api.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDto.UserDto signUp(UserRequestDto.SignUp signUp);
    UserResponseDto.UserDto createUser(UserRequestDto.CreateUser createUser);
    UserResponseDto.UserDto updateUser(UserRequestDto.CreateUser updateUser, Long id);
    String login(UserRequestDto.Login login);
    UserResponseDto.UserDto getUser(String phone);
    List<UserResponseDto.UserDto> getAllUser();
    UserResponseDto.UserDto getMyUserWithAuth();
    void withdrawal();
    void withdrawal(String phone);
    String changePassword(UserRequestDto.Login newPw);
    void changeAlarm(Integer number);
    void changeFont(Integer number);
    void changeTheme(Integer number);
    void changeEngrave(String engrave);
}
