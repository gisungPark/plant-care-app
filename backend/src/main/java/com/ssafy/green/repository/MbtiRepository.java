package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti, Long> {
}
