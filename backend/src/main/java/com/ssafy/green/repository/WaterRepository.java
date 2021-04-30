package com.ssafy.green.repository;

import com.ssafy.green.model.entity.plant.PlantCare;
import com.ssafy.green.model.entity.plant.Water;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaterRepository extends JpaRepository<Water, Long> {
    Optional<Water> findTopByPlantCareOrderByWaterDateDesc(PlantCare plantCare);
}
