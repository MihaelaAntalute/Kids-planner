package com.example.kidsplanner.repository;

import com.example.kidsplanner.model.Activity;
import com.example.kidsplanner.model.Period;
import com.example.kidsplanner.model.PeriodActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodActivityRepository extends JpaRepository<PeriodActivity,Long> {
    public PeriodActivity findByActivityAndPeriod(Activity activity, Period period);
}
