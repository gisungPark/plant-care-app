package com.ssafy.green.repository;

import com.ssafy.green.model.entity.Message;
import com.ssafy.green.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByUserAndTitleAndContentOrderByIdDesc(User user,String title, String content);

    Optional<Message> findByUserAndMessageKey(User user, String messageKey);

    List<Message> findAllByUserAndFlagOrderByIdDesc(User user, boolean flag);
}
