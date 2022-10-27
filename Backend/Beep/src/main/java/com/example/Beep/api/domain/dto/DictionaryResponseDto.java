package com.example.Beep.api.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder // 암호 의미 둘다 쓸거기 때문에 최상단에 어노테이션 적용
@Data
public class DictionaryResponseDto {

    String word; //암호
    String value;   //의미

}
