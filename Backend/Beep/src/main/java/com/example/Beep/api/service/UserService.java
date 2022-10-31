package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.User;

import java.util.Optional;

public interface UserService {
    User signUp(UserRequestDto.SignUp signUp);
    String login(UserRequestDto.Login login);
    User getUser(String phone);
    Optional<User> getMyUserWithAuth();
    void withdrawal();
    void withdrawal(String phone);
    String findPassword(String phone);
    String changePassword(UserRequestDto.Login newPw);
    void blockUser(UserRequestDto.Block block);
    void blockDelete(UserRequestDto.Block block);
}
