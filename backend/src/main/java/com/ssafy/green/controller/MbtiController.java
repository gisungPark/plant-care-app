package com.ssafy.green.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.ssafy.green.model.dto.MbtiResponse;
import com.ssafy.green.model.dto.MbtiResultResponse;
import com.ssafy.green.service.MbtiService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mbti")
@CrossOrigin("*")
public class MbtiController {
    @Autowired
    private MbtiService mbtiService;

    @ApiOperation(value = "mbti 질문, 답 리스트 조회", notes =
            "Response\n" +
            "- question : mbti 질문 \n" +
            "- opt1, opt2 : key - 결과유형, value - 선택지 \n" +
            "값 없을 때(null) : 토큰 검사 실패한 경우 ")
    @GetMapping("/all")
    public List<MbtiResponse> findAll(){
        List<MbtiResponse> list = null;
        try{
            //FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            list = mbtiService.findAll();
        }
        //catch (FirebaseAuthException e) {
        catch (Exception e) {

        }
        return list;
    }

    @ApiOperation(value = "mbti 결과 조회", notes =
            "Response\n" +
                    "- type : mbti type \n" +
                    "- summary : 타입요약 \n" +
                    "- etc : mbti 설명 (재미요소) \n" +
                    "- pid1 : 추천식물1 id \n" +
                    "- pid2 : 추천식물2 id \n" +
                    "값 없을 때(null) : 토큰 검사 실패한 경우 ")
    @GetMapping("/result/{typeStr}")
    public MbtiResultResponse Result(@PathVariable String typeStr){
        MbtiResultResponse res = null;
        try{
            //FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            res=mbtiService.findMyType(typeStr);
        }
        //catch (FirebaseAuthException e) {
        catch (Exception e) {

        }
        return res;
    }
}
