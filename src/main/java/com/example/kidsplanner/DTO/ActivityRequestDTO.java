package com.example.kidsplanner.DTO;

import com.example.kidsplanner.model.ActivityType;

public class ActivityRequestDTO {

    private String username;
    private Long periodId;
    private String activityType;
    private String description;

    public ActivityRequestDTO(String username, Long periodId, String activityType, String description) {
        this.username = username;
        this.periodId = periodId;
        this.activityType = activityType;
        this.description = description;
    }

    public ActivityRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
