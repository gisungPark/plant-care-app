package com.ssafy.green.model.dto.plant;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MyPlantRequest {
    private Long pid;
    private Long rid;
    private String nickname;
    private LocalDate started_date;
}
