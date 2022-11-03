package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.PhoneBookRequestDto;
import com.example.Beep.api.domain.dto.PhoneBookResponseDto;

import java.util.List;

public interface PhoneBookService {
    void registerPhone(List<PhoneBookRequestDto.Register> registerList);
    List<PhoneBookResponseDto.Phone> getPhoneList();
}
