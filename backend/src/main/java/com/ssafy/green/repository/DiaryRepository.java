package com.ssafy.green.repository;


import com.ssafy.green.model.entity.Diary;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, String> {

    List<Diary> findByUserAndFlag(User user, boolean flag);

}
