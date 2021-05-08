package com.ssafy.green.service;

import com.ssafy.green.model.dto.MbtiResponse;
import com.ssafy.green.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MbtiService {

    @Autowired
    private final MbtiRepository mbtiRepository;

    //모든 질문 조회
    public List<MbtiResponse> findAll() {
        return mbtiRepository.findAll().stream()
                .map(MbtiResponse::new)
                .collect(Collectors.toList());
    }
}
