package com.example.kidsplanner.model;

public enum EvaluationType {
    SUN(10),
    CLOUD(8),
    RAIN(6),
    STORM(4);

    public final Integer value;

    EvaluationType(Integer value) {
        this.value = value;
    }

    public Integer getEvaluationValue (){
        return this.value;
    }

}
