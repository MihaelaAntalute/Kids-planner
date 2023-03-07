package com.example.kidsplanner.DTO;

import com.example.kidsplanner.model.Activity;

import java.time.LocalDate;
import java.util.List;

public class PeriodResponseDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private Double averageEvaluation;
    private String username;
    private List<Activity> activityList;

    public PeriodResponseDTO(LocalDate startDate, LocalDate endDate, Double averageEvaluation, String username, List<Activity> activityList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageEvaluation = averageEvaluation;
        this.username = username;
        this.activityList = activityList;
    }

    public PeriodResponseDTO() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getAverageEvaluation() {
        return averageEvaluation;
    }

    public void setAverageEvaluation(Double averageEvaluation) {
        this.averageEvaluation = averageEvaluation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }
}
