package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.DictionaryResponseDto;
import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.entity.Preset;
import com.example.Beep.api.service.PresetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preset")
public class PresetController {

    @Autowired
    PresetServiceImpl presetService;

    @PostMapping("/save")
    public void PresetSave(@RequestBody PresetRequestDto presetRequestDto){
        presetService.PresetSave(presetRequestDto);
    }

    @DeleteMapping("/delete/{pid}")
    public void PresetDelete(@PathVariable("pid") Long pid){
        presetService.PresetDelete(pid);
    }

    @GetMapping("/find/{uid}")
    public ResponseEntity<?> PresetFind(@PathVariable("uid") Long uid){
        return new ResponseEntity<List<PresetResponseDto>>(presetService.PresetFind(uid),HttpStatus.OK);
    }
}
