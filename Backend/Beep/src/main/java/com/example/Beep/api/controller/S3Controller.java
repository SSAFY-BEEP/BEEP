package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.ApiResult;
import com.example.Beep.api.domain.dto.S3RequestDto;
import com.example.Beep.api.domain.enums.S3Type;
import com.example.Beep.api.service.S3Service;
import com.example.Beep.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(value = "5. S3(음성메세지/인사말)", tags={"5. S3(음성메세지/인사말)"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;
    private final UserService userService;

    @PostMapping("/voice")
    @ApiOperation(value = "음성메세지 등록")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ApiResult<?> uploadVoice(@RequestPart MultipartFile voice) {
        String voiceUrl = s3Service.uploadFile(voice);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @PostMapping("/introduce")
    @ApiOperation(value = "인사말 등록")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ApiResult<?> uploadIntroduce(@RequestPart MultipartFile voice) {
        //S3에 파일 등록
        String voiceUrl = s3Service.persistFile(voice);
        //DB 유저 introduce 수정
        userService.changeIntroduceAudio(voiceUrl);

        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @PatchMapping("/introduce")
    @ApiOperation(value = "인사말 삭제")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ApiResult<?> deleteIntroduce(@RequestBody S3RequestDto.introduceAudio introduceAudio) {
        //S3에서 introduceAudio 파일 찾아서 삭제
        s3Service.deleteFile(introduceAudio.getIntroduceAudio(), S3Type.PERMANENT.getNum());

       //DB 유저 introduce 수정
        userService.changeIntroduceAudio(null);
        return new ApiResult<>("Success", HttpStatus.OK);
    }

    @GetMapping("/voice")
    @ApiOperation(value = "인사말 파일명 조회(유저토큰)")
    @PreAuthorize("hasAnyRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ApiResult<?> findUserVoice() {
        String voiceUrl = s3Service.findUserVoice();
        return new ApiResult<>(voiceUrl, HttpStatus.OK);
    }

    @GetMapping("/voice/{phoneNumber}")
    @ApiOperation(value = "인사말 파일명 조회(핸드폰번호)")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ApiResult<?> findUserVoice(@PathVariable String phoneNumber) {
        String voiceUrl = s3Service.findUserVoiceByPhoneNumber(phoneNumber);
        return new ApiResult<>(voiceUrl, HttpStatus.OK);
    }
}

