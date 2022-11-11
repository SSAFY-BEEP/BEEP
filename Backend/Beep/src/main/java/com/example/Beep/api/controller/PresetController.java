package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.ApiResult;
import com.example.Beep.api.domain.dto.PresetRequestDto;
import com.example.Beep.api.domain.dto.PresetResponseDto;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.service.PresetServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "7. 프리셋",tags={"7. 프리셋"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/preset")
public class PresetController {

    private final PresetServiceImpl presetService;

    @ApiOperation(value = "프리셋 설정 및 업데이트(유저토큰)", notes = "프리셋 설정 및 업데이트(pid=1 : 메세지, pid=2 : 연락처)")
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> PresetSave(@RequestBody PresetRequestDto presetRequestDto){
        presetService.PresetSave(presetRequestDto);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "프리셋 삭제", notes = "프리셋id로 프리셋 삭제")
    @DeleteMapping("/delete/{pid}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ApiResult<?> PresetDelete(@PathVariable("pid") Long pid){
        try{
            presetService.PresetDelete(pid);
        } catch (Exception e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "유저 프리셋 찾기(유저토큰)", notes = "유저 프리셋 찾기")
    @GetMapping("/find")
    @PreAuthorize("hasAnyRole('USER')")
    public ApiResult<?> PresetFindByToken(){
        return new ApiResult<List<PresetResponseDto>>(presetService.PresetFind(null),HttpStatus.OK);
    }

    @ApiOperation(value = "유저 프리셋 찾기(관리자)", notes = "유저 프리셋 찾기")
    @GetMapping("/find/{uid}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResult<?> PresetFind(@PathVariable("uid") Long uid){
        return new ApiResult<List<PresetResponseDto>>(presetService.PresetFind(uid),HttpStatus.OK);
    }
}
