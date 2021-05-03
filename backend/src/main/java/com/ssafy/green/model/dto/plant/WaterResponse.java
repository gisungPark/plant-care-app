package com.ssafy.green.model.dto.plant;

import com.ssafy.green.model.entity.plant.Water;
import lombok.Data;

import java.io.Serializable;

@Data
public class WaterResponse implements Serializable {
    private Long wid;
    private String waterDate;

    public WaterResponse(Water entity) {
        this.wid = entity.getId();
        this.waterDate = entity.getWaterDate();
    }
}
