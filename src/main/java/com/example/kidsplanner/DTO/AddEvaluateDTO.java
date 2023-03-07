package com.example.kidsplanner.DTO;

import com.example.kidsplanner.model.EvaluationType;


public class AddEvaluateDTO {
    private Long activityId;
    private Long periodId;
    private EvaluationType evaluationType;

    public AddEvaluateDTO(Long activityId, Long periodId, EvaluationType evaluationType) {
        this.activityId = activityId;
        this.periodId = periodId;
        this.evaluationType = evaluationType;
    }

    public AddEvaluateDTO() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }
}
