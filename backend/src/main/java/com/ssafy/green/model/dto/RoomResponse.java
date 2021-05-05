package com.ssafy.green.model.dto;

import com.ssafy.green.model.dto.plant.MyPlantListResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse {
    private Long rid;
    private String roomName;
    private List<MyPlantListResponse> plantList;

    public RoomResponse(Room room){
        this.rid = room.getId();
        this.roomName = room.getRoomName();
        this.plantList = this.getPlantList(room);
    }

    public static List<MyPlantListResponse> getPlantList(Room room){
        List<MyPlantListResponse> list = new ArrayList<>();
        for(PlantCare plant : room.getPlantList()){
            if(plant.isFlag() && plant.isDead())
                list.add(new MyPlantListResponse(plant));
        }
        return list;
    }
}

