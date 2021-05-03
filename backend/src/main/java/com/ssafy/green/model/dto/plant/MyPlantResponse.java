package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Data;
import com.ssafy.green.model.entity.plant.PlantCare;

@Data
public class MyPlantResponse {
    private Long pid;
    private String nickname;
    private String startedDate;
    private String lastDate;
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
        this.startedDate = plantCare.getStartedDate();
        this.lastDate = plantCare.getLastDate();
        this.image = plantCare.getImage();
        this.name = plantCare.getName();
        this.water = plantCare.getWater();

        this.common = plantInfo.getCommon();
        this.temp = plantInfo.getTemp();
        this.humid = plantInfo.getHumid();
        this.info = plantInfo.getInfo();
    }
}
