package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Message;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByUserAndTitleAndContentAndFlagOrderByIdDesc(User user,String title, String content, boolean flag);

    List<Message> findAllByUser(User user);
}
