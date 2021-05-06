package com.ssafy.green.model.dto;

import com.ssafy.green.model.entity.Mbti;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class MbtiResponse {
    private String question;
    private HashMap<String, String> opt1 = new HashMap<>();
    private HashMap<String, String> opt2 = new HashMap<>();

    public MbtiResponse(Mbti entity){
        this.question = entity.getQuestion();
        this.opt1.put("val", entity.getVal1());
        this.opt1.put("answer", entity.getOpt1());
        this.opt2.put("val", entity.getVal2());
        this.opt2.put("answer", entity.getOpt2());
    }

}
