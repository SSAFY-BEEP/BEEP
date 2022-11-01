package com.example.Beep.api.domain.dto;

import lombok.Builder;

public class S3RequestDto {

    String sphoneNumber;
    String rphoneNumber;

    @Builder
    S3RequestDto(String sphoneNumber,String rphoneNumber){
        this.sphoneNumber=sphoneNumber;
        this.rphoneNumber=rphoneNumber;
    }
}
