package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Mbti;
import com.ssafy.green.model.entity.MbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MbtiResultRepository extends JpaRepository<MbtiResult, Long> {
    Optional<MbtiResult> findByType(String strType);
}
