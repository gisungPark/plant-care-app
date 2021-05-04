package com.ssafy.green.model.dto.plant;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaterRequest {
    private Long pid;
    private String waterDate;
}