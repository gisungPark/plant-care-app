package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.Data;

@Data
public class plantResponse {
    private Long id;
    private String name;
    private String imgUrl;

    public static plantResponse create(PlantCare p) {
        plantResponse response = new plantResponse();
        response.id = p.getId();
        response.name = p.getNickname();
        response.imgUrl = p.getImage();
        return response;
    }
}
