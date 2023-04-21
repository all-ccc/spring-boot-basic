package com.chaminju.firstproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data // Setter, Getter, Required, ToString, EqualsAndHashcode
@Builder
@AllArgsConstructor // 전체 생성자
public class ExampleResponseDto {
    private String data1;
    private String data2;
    private String data3;
}
