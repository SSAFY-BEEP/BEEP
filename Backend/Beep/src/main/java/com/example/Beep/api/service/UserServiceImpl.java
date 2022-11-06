package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.dto.UserResponseDto;
import com.example.Beep.api.domain.enums.Authority;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.domain.enums.MessageType;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.BlockRepository;
import com.example.Beep.api.repository.MessageRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import com.example.Beep.api.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final MessageRepository messageRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto.UserDto signUp(UserRequestDto.SignUp signUp) {
        if(userRepository.findByPhoneNumber(signUp.getPhoneNumber()).orElse(null) != null) {
            throw new CustomException(ErrorCode.METHOD_NO_CONTENT);
        }

        User user = User.builder()
                .phoneNumber(signUp.getPhoneNumber())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .fcmToken(signUp.getFcmToken())
                .build();

        UserResponseDto.UserDto res = new UserResponseDto.UserDto();
        res.of(userRepository.save(user));
        return res;
    }

    @Override
    public UserResponseDto.UserDto createUser(UserRequestDto.CreateUser createUser) {
        if(userRepository.findByPhoneNumber(createUser.getPhoneNumber()).orElse(null) != null) {
            throw new CustomException(ErrorCode.METHOD_NO_CONTENT);
        }

        User user = User.builder()
                .phoneNumber(createUser.getPhoneNumber())
                .password(passwordEncoder.encode(createUser.getPassword()))
                .fcmToken(createUser.getFcmToken())
                .authority(createUser.getAuthority())
                .engrave(createUser.getEngrave())
                .alarm(createUser.getAlarm())
                .font(createUser.getFont())
                .introduceAudio(createUser.getIntroduceAudio())
                .theme(createUser.getTheme())
                .build();

        UserResponseDto.UserDto res = new UserResponseDto.UserDto();
        res.of(userRepository.save(user));
        return res;
    }

    @Override
    public UserResponseDto.UserDto updateUser(UserRequestDto.CreateUser updateUser, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        if(userRepository.findByPhoneNumber(updateUser.getPhoneNumber()).orElse(null) != null) {
            throw new CustomException(ErrorCode.METHOD_ALREADY_REPORTED);
        }

        User update = User.builder()
                .id(id)
                .phoneNumber(updateUser.getPhoneNumber() == null ? user.getPhoneNumber() : updateUser.getPhoneNumber())
                .password(passwordEncoder.encode(updateUser.getPassword() == null ? user.getPassword() : updateUser.getPassword()))
                .fcmToken(updateUser.getFcmToken() == null ? user.getFcmToken() : updateUser.getFcmToken())
                .authority(updateUser.getAuthority() == null ? user.getAuthority() : updateUser.getAuthority())
                .engrave(updateUser.getEngrave() == null ? user.getEngrave() : updateUser.getEngrave())
                .alarm(updateUser.getAlarm() == null ? user.getAlarm() : updateUser.getAlarm())
                .font(updateUser.getFont() == null ? user.getFont() : updateUser.getFont())
                .introduceAudio(updateUser.getIntroduceAudio() == null ? user.getIntroduceAudio() : updateUser.getIntroduceAudio())
                .theme(updateUser.getTheme() == null ? user.getTheme() : updateUser.getTheme())
                .build();

        UserResponseDto.UserDto res = new UserResponseDto.UserDto();
        res.of(userRepository.save(user));
        return res;
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
    public UserResponseDto.UserDto getUser(String phone) {
        UserResponseDto.UserDto user = new UserResponseDto.UserDto();
        user.of(userRepository.findByPhoneNumber(phone).get());
        return user;
    }

    @Override
    public List<UserResponseDto.UserDto> getAllUser() {

        List<User> list = userRepository.findAll();
        List<UserResponseDto.UserDto> result = new ArrayList<>();
        for(User tmp : list) {
            UserResponseDto.UserDto res = new UserResponseDto.UserDto();
            res.of(tmp);
            result.add(res);
        }
        return result;
    }

    @Transactional
    @Override
    public UserResponseDto.UserDto getMyUserWithAuth() {
        UserResponseDto.UserDto user = new UserResponseDto.UserDto();
        user.of(SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhoneNumber).get());
        return user;
    }

    @Override
    @Transactional
    public void withdrawal() {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        deleteData(user);
    }

    @Override
    public void withdrawal(String phone) {
        User user = userRepository.findByPhoneNumber(phone).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        deleteData(user);
    }

    //유저 회원 탈퇴 및 데이터 지우기
    public void deleteData(User user) {
        user.withdrawal(user.getId().toString(),"0","0",Authority.ROLE_LEAVE);
        messageRepository.deleteMessageByOwnerId(user.getId());
        blockRepository.deleteBlockByUserIdOrTargetId(user.getId(), user.getId());
        messageRepository.deleteMessagesBySenderIdOrReceiverIdAndType(user.getId(), user.getId(), MessageType.BLOCK.getNum());
        userRepository.save(user);
    }

    @Override
    public String changePassword(UserRequestDto.Login newPw) {
        User user = userRepository.findByPhoneNumber(newPw.getPhoneNumber()).orElse(null);
        if(user == null) return "존재하지 않는 회원입니다.";
        user.changePw(passwordEncoder.encode(newPw.getPassword()));
        userRepository.save(user);
        return "Success";
    }

    @Override
    public void changeAlarm(Integer number) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        user.changeConfig(user.getEngrave(), user.getTheme(), user.getFont(), number);
        userRepository.save(user);
    }

    @Override
    public void changeFont(Integer number) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        user.changeConfig(user.getEngrave(), user.getTheme(), number, user.getAlarm());
        userRepository.save(user);
    }

    @Override
    public void changeTheme(Integer number) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        user.changeConfig(user.getEngrave(), number, user.getFont(), user.getAlarm());
        userRepository.save(user);
    }

    @Override
    public void changeEngrave(String engrave) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        user.changeConfig(engrave, user.getTheme(), user.getFont(), user.getAlarm());
        userRepository.save(user);
    }

}
