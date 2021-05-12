package com.ssafy.green.service.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Message{
        private String token;
        private Notification notification;
        private Data data;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Notification{

        private String title;
        private String body;
        private String image;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Data{
        private String msgId;
    }

}
