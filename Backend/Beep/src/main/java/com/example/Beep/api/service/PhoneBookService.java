package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PhoneBookRequestDto;
import com.example.Beep.api.domain.dto.PhoneBookResponseDto;

import java.util.List;

public interface PhoneBookService {
    void registerPhone(List<PhoneBookRequestDto.Register> registerList);
    List<PhoneBookResponseDto.Phone> getPhoneList();
    PhoneBookResponseDto.Phone updatePhone(String phone, PhoneBookRequestDto.Register update);
    void deletePhone(String phone);
}
