package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.DictionaryResponseDto;
import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.entity.Preset;
import com.example.Beep.api.service.PresetServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "프리셋 설정",tags={"프리셋 설정 API"})
@RestController
@RequestMapping("/preset")
public class PresetController {

    @Autowired
    PresetServiceImpl presetService;

    @ApiOperation(value = "프리셋 설정 및 업데이트", notes = "프리셋 설정 및 업데이트")
    @PostMapping("/save")
    public void PresetSave(@RequestBody PresetRequestDto presetRequestDto){
        presetService.PresetSave(presetRequestDto);
    }

    @ApiOperation(value = "프리셋 삭제", notes = "프리셋 삭제")
    @DeleteMapping("/delete/{pid}")
    public void PresetDelete(@PathVariable("pid") Long pid){
        presetService.PresetDelete(pid);
    }

    @ApiOperation(value = "유저 프리셋 찾기", notes = "유저 프리셋 찾기")
    @GetMapping("/find/{uid}")
    public ResponseEntity<?> PresetFind(@PathVariable("uid") Long uid){
        return new ResponseEntity<List<PresetResponseDto>>(presetService.PresetFind(uid),HttpStatus.OK);
    }
}
