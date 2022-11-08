package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

public class S3RequestDto {
    @Getter
    @NoArgsConstructor
    public static class introduceAudio {
        String introduceAudio;
    }

    @Getter
    @Builder
    public static class sendMessage24 {
        private String content;
        private String receiverNum;
    }
}
