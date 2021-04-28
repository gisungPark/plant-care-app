package com.ssafy.green.repository;

import com.ssafy.green.model.entity.plant.Water;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterRepository extends JpaRepository<Water, Long> {
}
