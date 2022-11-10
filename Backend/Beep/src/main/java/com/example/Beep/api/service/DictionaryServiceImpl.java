package com.example.Beep.api.service;

import com.example.Beep.api.domain.dto.DictionaryResponseDto;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DictionaryServiceImpl implements DictionaryService{

    private  final DictionaryRepository dictionaryRepository;

    public List<DictionaryResponseDto> FindWord(String word) {
        List<DictionaryResponseDto>dictionaryResponseDtoList=dictionaryRepository.findByWordContaining(word)
                .stream()
                .map(Dictionary -> DictionaryResponseDto.builder()
                        .word(Dictionary.getWord())
                        .value(Dictionary.getValue())
                        .build()).collect(Collectors.toList());

        return dictionaryResponseDtoList;
    }

    public List<DictionaryResponseDto> FindValue(String value) {
        List<DictionaryResponseDto>dictionaryResponseDtoList=dictionaryRepository.findByValueContaining(value)
                .stream()
                .map(Dictionary -> DictionaryResponseDto.builder()
                        .word(Dictionary.getWord())
                        .value(Dictionary.getValue())
                        .build()).collect(Collectors.toList());

        return dictionaryResponseDtoList;
    }

    @Override
    public List<DictionaryResponseDto> FindRandom() {
        try{
            List<DictionaryResponseDto>dictionaryResponseDtoList=dictionaryRepository.findRandom()
                    .stream()
                    .map(Dictionary -> DictionaryResponseDto.builder()
                            .word(Dictionary.getWord())
                            .value(Dictionary.getValue())
                            .build()).collect(Collectors.toList());

            return dictionaryResponseDtoList;
        } catch (Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }
}
