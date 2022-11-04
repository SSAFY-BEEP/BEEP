package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.S3RequestDto;
import com.example.Beep.api.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import java.io.IOException;
import java.util.List;

@Api(value = "5. S3(음성메세지/인사말)", tags={"5. S3(음성메세지/인사말)"})
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/s3")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/voice")
    @ApiOperation(value = "음성메세지 1개 등록")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> uploadVoice(@RequestPart MultipartFile voice) {
        System.out.println(voice+"확인하기");
        String voiceUrl = s3Service.uploadFile(voice);
        System.out.println(voiceUrl+"url 주소");
        return ResponseEntity.ok().body(voiceUrl);
    }

    @PostMapping("/introduce")
    @ApiOperation(value = "인사말 1개 등록")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> uploadIntroduce(@RequestPart MultipartFile voice) {
        System.out.println(voice+"확인하기");
        String voiceUrl = s3Service.persistFile(voice);
        System.out.println(voiceUrl+"url 주소");
        return ResponseEntity.ok().body(voiceUrl);
    }

    @PostMapping("/persist/voice")
    @ApiOperation(value = "영구 보관함 음성메세지 1개 등록")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> uploadPersistVoice(@RequestPart MultipartFile voice) {
        System.out.println(voice+"확인하기");
        String voiceUrl = s3Service.persistFile(voice);
        System.out.println(voiceUrl+"url 주소");
        return ResponseEntity.ok().body(voiceUrl);
    }


    @GetMapping("/voice{userId}")
    @ApiOperation(value = "인사말 파일 주소 찾기")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> findUserVoice(@PathVariable("userId") Long userId) {
        String voiceUrl = s3Service.findUserVoice(userId);
        return ResponseEntity.ok().body(voiceUrl);
    }


}

