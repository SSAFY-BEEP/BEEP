package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.User;

import java.util.Optional;

public interface UserService {
    User signUp(UserRequestDto.SignUp signUp);
    String login(UserRequestDto.Login login);
    User getUser(String phone);
    Optional<User> getMyUserWithAuth();
}
