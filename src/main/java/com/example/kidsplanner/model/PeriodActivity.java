package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
@Entity
public class PeriodActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private Boolean isDoneParent;

    @Column
    private Boolean isDoneChild;

    @Column
    private EvaluationType evaluationType;

    @ManyToOne
    @JoinColumn(name = "activity_Id")
    @JsonBackReference(value = "activity-periodActivity")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "period_Id")
    @JsonBackReference(value = "period-activity")
    private Period period;


    public PeriodActivity(){}

    public PeriodActivity(Long id, Boolean isDoneParent, Boolean isDoneChild, EvaluationType evaluationType, Activity activity, Period period) {
        this.id = id;
        this.isDoneParent = isDoneParent;
        this.isDoneChild = isDoneChild;
        this.evaluationType = evaluationType;
        this.activity = activity;
        this.period = period;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDoneParent() {
        return isDoneParent;
    }

    public void setDoneParent(Boolean doneParent) {
        isDoneParent = doneParent;
    }

    public Boolean getDoneChild() {
        return isDoneChild;
    }

    public void setDoneChild(Boolean doneChild) {
        isDoneChild = doneChild;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
