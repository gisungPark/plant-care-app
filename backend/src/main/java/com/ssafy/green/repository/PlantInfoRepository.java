package com.ssafy.green.repository;

import com.ssafy.green.model.entity.plant.PlantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantInfoRepository extends JpaRepository<PlantInfo, Long> {
    List<PlantInfo> findByCommonContaining(String search);
    List<PlantInfo> findByNameContaining(String search);
    Optional<PlantInfo> findByCommon(String common);
}
