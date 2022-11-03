package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PhoneBookRequestDto;
import com.example.Beep.api.domain.dto.PhoneBookResponseDto;
import com.example.Beep.api.domain.entity.PhoneBook;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.PhoneBookRepository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneBookServiceImpl implements PhoneBookService {

    private final PhoneBookRepository phoneBookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void registerPhone(List<PhoneBookRequestDto.Register> registerList) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));

        for(PhoneBookRequestDto.Register register : registerList) {
            //연락처 중복 안되게
            if(phoneBookRepository.findPhoneBookByTargetPhoneAndUserId(register.getPhone(), user.getId()).isPresent()){
                throw new CustomException(ErrorCode.METHOD_ALREADY_REPORTED);
            }
            User target = userRepository.findByPhoneNumber(register.getPhone()).orElse(null);
            PhoneBook tmp = PhoneBook.builder()
                    .name(register.getName())
                    .targetPhone(register.getPhone())
                    .user(user)
                    .install(target == null ? 0:1)
                    .build();
            phoneBookRepository.save(tmp);
        }
    }

    @Override
    public List<PhoneBookResponseDto.Phone> getPhoneList() {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));

        List<PhoneBook> list = phoneBookRepository.findByUserId(user.getId());
        List<PhoneBookResponseDto.Phone> result = new ArrayList<>();
        for(PhoneBook tmp : list) {
            PhoneBookResponseDto.Phone phone = PhoneBookResponseDto.Phone.builder()
                    .phone(tmp.getTargetPhone())
                    .name(tmp.getName())
                    .install(tmp.getInstall())
                    .build();
            result.add(phone);
        }
        return result;
    }

    @Override
    public PhoneBookResponseDto.Phone updatePhone(String phone, PhoneBookRequestDto.Register update) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        PhoneBook target = phoneBookRepository.findPhoneBookByTargetPhoneAndUserId(phone, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        target.update(update.getPhone(), update.getName());
        phoneBookRepository.save(target);
        PhoneBookResponseDto.Phone result = PhoneBookResponseDto.Phone.builder()
                .phone(target.getTargetPhone())
                .name(target.getName())
                .install(target.getInstall())
                .build();
        return result;
    }

    @Override
    public void deletePhone(String phone) {
        User user = userRepository.findByPhoneNumber(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        PhoneBook target = phoneBookRepository.findPhoneBookByTargetPhoneAndUserId(phone, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.METHOD_NO_CONTENT));
        phoneBookRepository.delete(target);
    }
}
