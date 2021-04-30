package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.model.entity.plant.Water;
import lombok.Data;
import com.ssafy.green.model.entity.plant.PlantCare;

import java.time.LocalDate;

@Data
public class MyPlantResponse {
    private Long pid;
    private String nickname;
    private LocalDate started_date;
    private LocalDate last_date;
    private String image;

    private String common;
    private String name;
    private String water;
    private String temp;
    private String humid;
    private String info;

    public MyPlantResponse(PlantCare plantCare, PlantInfo plantInfo) {
        this.pid = plantCare.getId();
        this.nickname = plantCare.getNickname();
        this.started_date = plantCare.getStartedDate();
        this.last_date = plantCare.getLastDate();
        this.image = plantCare.getImage();
        this.name = plantCare.getName();
        this.water = plantCare.getWater();

        this.common = plantInfo.getCommon();
        this.temp = plantInfo.getTemp();
        this.humid = plantInfo.getHumid();
        this.info = plantInfo.getInfo();
    }
}
