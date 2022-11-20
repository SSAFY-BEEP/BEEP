package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.ApiResult;
import com.example.Beep.api.domain.dto.DictionaryResponseDto;
import com.example.Beep.api.service.DictionaryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "8. 사전", tags={"8. 사전"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/dictionary")
public class DictionaryController {

    private final DictionaryServiceImpl dictionaryService;

    @GetMapping({"/word/{word}", "/word"})
    @ApiOperation(value = "사전 단어 검색", notes = "단어(초성/숫자)로 사전에서 뜻 검색")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> FindWord(@PathVariable (value = "word", required = false) String word){
        return new ApiResult<List<DictionaryResponseDto>>(dictionaryService.FindWord(word), HttpStatus.OK);
    }

    @GetMapping("/value/{value}")
    @ApiOperation(value = "사전 뜻 검색", notes = "뜻으로 사전에서 뜻 검색")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?> FindValue(@PathVariable ("value") String value){
        return new ApiResult<List<DictionaryResponseDto>>(dictionaryService.FindValue(value), HttpStatus.OK);
    }

    @GetMapping("/random")
    @ApiOperation(value = "사전 랜덤 5개", notes = "랜덤으로 5개 사전 데이터 리턴")
    @PreAuthorize("hasRole('USER')")
    public ApiResult<?>Random(){
        return new ApiResult<List<DictionaryResponseDto>>(dictionaryService.FindRandom(),HttpStatus.OK);
    }
}
