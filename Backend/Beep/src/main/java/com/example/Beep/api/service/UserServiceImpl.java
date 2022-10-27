package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import com.example.Beep.api.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User signUp(UserRequestDto.SignUp signUp) {
        if(userRepository.findByPhoneNumber(signUp.getPhoneNumber()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .phoneNumber(signUp.getPhoneNumber())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .fcmToken(signUp.getFcmToken())
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(UserRequestDto.Login login) {
        //Login 정보를 통해서 authenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getPhoneNumber(), login.getPassword());

        //토큰을 통해서 Authentication 객체를 생성하기 위해 authenticate 메소드를 실행
        //authenticate 메서드가 실행될 때 CustomUserDetailsService의 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //결과값을 컨텍스트에 저장하고 createToken을 통해 토큰도 생성함
        String jwt = tokenProvider.createToken(authentication);

        return jwt;
    }

    @Override
    public User getUser(String phone) {
        return userRepository.findByPhoneNumber(phone).get();
    }

    @Transactional
    @Override
    public Optional<User> getMyUserWithAuth() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhoneNumber);
    }

}
