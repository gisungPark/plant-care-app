package com.ssafy.green.repository;

import com.ssafy.green.model.entity.plant.PlantCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantCare, Long> {
    List<PlantCare> findAllByDeadAndFlag(boolean dead, boolean flag);
}
