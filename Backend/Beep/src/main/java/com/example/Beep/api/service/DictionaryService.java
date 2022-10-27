package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.DictionaryResponseDto;

import java.util.List;

public interface DictionaryService {

    List<DictionaryResponseDto> FindWord(String word);

    List<DictionaryResponseDto> FindValue(String value);

    List<DictionaryResponseDto>FindRandom();
}
