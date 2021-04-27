package com.ssafy.green.model.dto;

import com.ssafy.green.model.dto.plant.MyPlantResponse;
import com.ssafy.green.model.entity.Room;
import com.ssafy.green.model.entity.plant.PlantCare;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomResponse {
    private Long rid;
    private String roomName;
    private List<MyPlantResponse> plantList;

    public RoomResponse(Room room){
        this.rid = room.getId();
        this.roomName = room.getRoomName();
        this.plantList = this.getPlantList(room);
    }

    public List<MyPlantResponse> getPlantList(Room room){
        List<MyPlantResponse> list = new ArrayList<>();
        for(PlantCare plant : room.getPlantList()){
            list.add(new MyPlantResponse(plant));
        }
        return list;
    }
}
