package com.example.kidsplanner.config;

import com.example.kidsplanner.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluatePeriodConfig {
    private PeriodService periodService;

    @Autowired
    public EvaluatePeriodConfig(PeriodService periodService) {
        this.periodService = periodService;
    }
}
