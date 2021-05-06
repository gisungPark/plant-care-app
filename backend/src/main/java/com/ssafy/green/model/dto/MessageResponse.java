package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateTime;
    private boolean flag;

    public static MessageResponse create(Message m) {
        MessageResponse response = new MessageResponse();
        response.setId(m.getId());
        response.setTitle(m.getTitle());
        response.setContent(m.getContent());
        response.setDateTime(m.getDateTime());
        response.setFlag(m.getFlag());
        return response;
    }
}
