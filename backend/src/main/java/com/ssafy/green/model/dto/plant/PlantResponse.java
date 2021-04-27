package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PlantResponse implements Serializable {
    private Long id;
    private String common;
    private String name;
    private String level;
    private String temp;
    private String humid;
    private String water;
    private String info;
    private String image;

    public PlantResponse(PlantInfo entity) {
        this.id = entity.getId();
        this.common = entity.getCommon();
        this.name = entity.getName();
        this.level = entity.getLevel();
        this.temp = entity.getTemp();
        this.humid = entity.getHumid();
        this.water = entity.getWater();
        this.info = entity.getInfo();
        this.image = entity.getImage();
    }
}
