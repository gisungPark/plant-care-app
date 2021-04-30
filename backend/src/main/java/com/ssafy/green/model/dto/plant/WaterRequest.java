package com.ssafy.green.model.dto.plant;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class WaterRequest {
    private Long pid;
    private LocalDate waterDate;
}