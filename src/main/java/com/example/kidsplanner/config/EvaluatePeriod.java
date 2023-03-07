package com.example.kidsplanner.config;

import com.example.kidsplanner.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EvaluatePeriod {

    private PeriodRepository periodRepository;

    @Autowired
    public EvaluatePeriod(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    private
    @Scheduled(cron = "0 15 10 15 * ?") void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }
}
