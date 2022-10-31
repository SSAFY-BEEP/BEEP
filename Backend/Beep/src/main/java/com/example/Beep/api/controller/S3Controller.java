package com.example.Beep.api.controller;

import com.example.Beep.api.service.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import java.io.IOException;
import java.util.List;

@Api(value = "S3 관리", tags={"S3 관리"})
@RequiredArgsConstructor
@RequestMapping("/s3")
@RestController
public class S3Controller {
    @Autowired
    S3Service s3Service;

    @PostMapping("/upload/image")
    @ApiOperation(value = "사진 1개 등록")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "권한 에러"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> uploadImage(@RequestPart MultipartFile image) {
        System.out.println(image+"확인하기");
        String photoUrl = s3Service.uploadFile(image);
        System.out.println(photoUrl+"url 주소");
        return ResponseEntity.ok().body(photoUrl);
    }

}

