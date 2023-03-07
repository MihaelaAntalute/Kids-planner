package com.example.kidsplanner.DTO;

import java.time.LocalDate;

public class PeriodRequestDTO {
    private LocalDate date;
    private Long periodId;

    public PeriodRequestDTO(LocalDate date, Long periodId) {
        this.date = date;
        this.periodId = periodId;
    }

    public PeriodRequestDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

}
