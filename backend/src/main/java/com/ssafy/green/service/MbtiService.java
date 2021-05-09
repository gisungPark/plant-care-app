package com.ssafy.green.service;

import com.ssafy.green.model.dto.MbtiResponse;
import com.ssafy.green.model.dto.MbtiResultResponse;
import com.ssafy.green.model.entity.MbtiResult;
import com.ssafy.green.model.entity.plant.PlantInfo;
import com.ssafy.green.repository.MbtiRepository;
import com.ssafy.green.repository.MbtiResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MbtiService {

    @Autowired
    private final MbtiRepository mbtiRepository;

    @Autowired
    private final MbtiResultRepository mbtiResultRepository;

    //모든 질문 조회
    public List<MbtiResponse> findAll() {
        return mbtiRepository.findAll().stream()
                .map(MbtiResponse::new)
                .collect(Collectors.toList());
    }

    public MbtiResultResponse findMyType(String typeStr){
        System.out.println(typeStr);
        int countE=0, countI=0, countS=0, countN=0, countF=0, countT=0, countJ=0, countP=0;

        for(int i=0; i<typeStr.length(); i++){
            if(typeStr.charAt(i)=='E') countE++;
            if(typeStr.charAt(i)=='I') countI++;
            if(typeStr.charAt(i)=='S') countS++;
            if(typeStr.charAt(i)=='N') countN++;
            if(typeStr.charAt(i)=='F') countF++;
            if(typeStr.charAt(i)=='T') countT++;
            if(typeStr.charAt(i)=='J') countJ++;
            if(typeStr.charAt(i)=='P') countP++;
        }

        String result="";
        if(countE>countI) result+="E";
        else result+="I";

        if(countS>countN) result+="S";
        else result+="N";

        if(countF>countT) result+="F";
        else result+="T";

        if(countJ>countP) result+="J";
        else result+="P";

        System.out.println(result);

        Optional<MbtiResult> mbtiR = mbtiResultRepository.findByType(result);

        PlantInfo p1 = mbtiR.get().getPlantInfo1();
        PlantInfo p2 = mbtiR.get().getPlantInfo2();
        List<PlantInfo> res = new ArrayList<>();
        res.add(p1); res.add(p2);


        return new MbtiResultResponse(res, mbtiR.get());
    }


}
