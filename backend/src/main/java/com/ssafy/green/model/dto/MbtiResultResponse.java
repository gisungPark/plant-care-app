package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.MbtiResult;
import com.ssafy.green.model.entity.plant.PlantInfo;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MbtiResultResponse {
    private String type;
    private String summagry;
    private String etc;

    @Data
    class recommendPlant {
        private Long id;
        private String name;
        private String image;

        public recommendPlant(PlantInfo p) {
            this.id = p.getId();
            this.name =p.getName();
            this.image=p.getImage();
        }
    }

    private List<recommendPlant> p=new ArrayList<>();

    public MbtiResultResponse(List<PlantInfo> ent, MbtiResult entity){
        this.type=entity.getType();
        this.summagry=entity.getSummary();
        this.etc=entity.getEtc();

        for (PlantInfo i: ent) {
            this.p.add(new recommendPlant(i));
        }
    }
}
