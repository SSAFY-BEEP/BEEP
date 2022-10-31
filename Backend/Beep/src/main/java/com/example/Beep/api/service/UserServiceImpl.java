package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.SMSRequest;
import com.example.Beep.api.domain.dto.UserRequestDto;
import com.example.Beep.api.domain.entity.Authority;
import com.example.Beep.api.domain.entity.Block;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.repository.BlockRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import com.example.Beep.api.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BlockRepository blockRepository;

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    @Value("${sms.url}")
    private String smsUrl;

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

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> getMyUserWithAuth() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByPhoneNumber);
    }

    @Override
    public void withdrawal() {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        user.update("0","0","0",Authority.ROLE_LEAVE);

        userRepository.save(user);
    }

    @Override
    public void withdrawal(String phone) {
        User user = userRepository.findByPhoneNumber(phone).get();
        user.update("0","0","0",Authority.ROLE_LEAVE);

        userRepository.save(user);
    }

    @Override
    public String findPassword(String phone) {

        //난수로 8자리 임시 비밀번호 생성
        UUID uid = UUID.randomUUID();
        String newPw = uid.toString().substring(0,8);
        //유저의 비밀번호를 임시 비밀번호로 변환
//        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get()).get();
        User user = userRepository.findByPhoneNumber(phone).orElse(null);
        if(user == null) return "존재하지 않는 회원입니다.";
        user.changePw(newPw);

        //뿌리오 api로 요청
        String msg = "임시비밀번호 : " + newPw;
        SMSRequest req = new SMSRequest(user.getPhoneNumber(), msg);
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Body set
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("userid", req.getUserid());
        body.add("callback", req.getCallback());
        body.add("phone", user.getPhoneNumber());
        body.add("msg", msg);
        // API 요청 객체
        HttpEntity entity = new HttpEntity(body, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        // 뿌리오 API 요청 (성공하면 문자가 감)
        ResponseEntity<String> res = restTemplate.exchange(smsUrl, HttpMethod.POST, entity, String.class);
        //결과 성공이면 유저에 저장 (메시지 api 연동이 안되서 디비에 반영 안됨)
        if(res.getBody().substring(0,2).equals("ok")) {
            userRepository.save(user);
        }
        //바뀐 비밀번호 리턴
        return newPw;
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
    @Transactional
    public void blockUser(UserRequestDto.Block block) {
        try{
            User user=userRepository.findByPhoneNumber(block.getPNum()).get();
            User Buser=userRepository.findByPhoneNumber(block.getBNum()).get();

            Block newBlock= Block.builder()
                    .user1(user)
                    .user2(Buser)
                    .build();
            blockRepository.save(newBlock);
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void blockDelete(UserRequestDto.Block block) {
        try{
            User Puser=userRepository.findByPhoneNumber(block.getPNum()).get();
            User Buser=userRepository.findByPhoneNumber(block.getBNum()).get();
            Block dBlock=blockRepository.findDelete(Puser.getId(),Buser.getId());
            blockRepository.deleteById(dBlock.getId());
        }catch (NullPointerException n){
            n.printStackTrace();
        }
    }


}
