package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Data;

@Data
public class MyPlantResponse {
    private Long id;
    private String nickname;
    private String name;
    private String imgUrl;

    public MyPlantResponse(PlantCare entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.imgUrl =entity.getImage();
    }
}
