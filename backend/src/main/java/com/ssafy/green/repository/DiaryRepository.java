package com.ssafy.green.repository;


import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query(value = "SELECT d FROM Diary d " +
                        "WHERE d.user.id = :id and d.flag = true ORDER BY d.id DESC")
    List<Diary> findAllDiary(@Param("id") Long id);

    Diary findByIdAndFlag(Long id, boolean flag);



}
