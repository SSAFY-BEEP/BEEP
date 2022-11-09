package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PresetResponseDto {

    Long pid;
    Long uid;
    Integer number;
    Integer part;
    String content;

    @Builder
    public PresetResponseDto(Long pid,Long uid,Integer number,Integer part,String content){
        this.pid=pid;
        this.uid=uid;
        this.number=number;
        this.part=part;
        this.content=content;
    }
}
