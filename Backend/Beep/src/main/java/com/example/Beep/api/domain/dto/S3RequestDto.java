package com.example.Beep.api.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class S3RequestDto {
    @Getter
    @Builder
    public static class introduceAudio {
        String introduceAudio;
    }

    @Getter
    @Builder
    public static class sendMessage24 {
        private MultipartFile file;
        private String content;
        private String receiverNum;
    }
}
