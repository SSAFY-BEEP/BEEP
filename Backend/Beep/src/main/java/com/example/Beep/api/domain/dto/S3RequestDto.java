package com.example.Beep.api.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

public class S3RequestDto {
    @Getter
    @Builder
    public static class introduceAudio {
        String introduceAudio;
    }
}
