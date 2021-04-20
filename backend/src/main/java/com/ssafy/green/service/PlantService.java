package com.ssafy.green.service;

import com.ssafy.green.repository.PlantCareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantCareRepository plantcareRepository;
}
