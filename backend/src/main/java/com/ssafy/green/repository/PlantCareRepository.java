package com.ssafy.green.repository;

import com.ssafy.green.model.entity.PlantsCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantCareRepository extends JpaRepository<PlantsCare, Long> {
}
