package com.example.Beep.api.domain.dto;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class S3RequestDto {
    @Getter
    @NoArgsConstructor
    public static class introduceAudio {
        String introduceAudio;
    }

    @Getter
    @Builder
    public static class sendMessage24 {
//        private MultipartFile file;
        private String content;
        private String receiverNum;
    }
}
