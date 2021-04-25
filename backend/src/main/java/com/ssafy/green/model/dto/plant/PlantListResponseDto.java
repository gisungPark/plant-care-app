package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PlantListResponseDto implements Serializable {
    private Long id;
    private String common;
    private String name;
    private String image;

    public PlantListResponseDto(PlantInfo entity) {
        this.id = entity.getId();
        this.common = entity.getCommon();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}
