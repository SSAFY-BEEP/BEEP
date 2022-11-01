package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User signUp(UserRequestDto.SignUp signUp);
    String login(UserRequestDto.Login login);
    User getUser(String phone);
    List<User> getAllUser();
    Optional<User> getMyUserWithAuth();
    void withdrawal();
    void withdrawal(String phone);
    String findPassword(String phone);
    String changePassword(UserRequestDto.Login newPw);
    void blockUser(UserRequestDto.Block block);
    void blockDelete(UserRequestDto.Block block);
    void changeAlarm(Integer number);
    void changeFont(Integer number);
    void changeTheme(Integer number);
    void changeEngrave(String engrave);
}
