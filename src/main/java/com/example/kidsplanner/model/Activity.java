package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private ActivityType activityType;


    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonBackReference(value = "user-activity")
    private User user;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "activity-periodActivity")
    private List<PeriodActivity> periodActivityList;

    @ManyToOne
    @JoinColumn(name = "period_Id")
    @JsonBackReference(value = "period-activity")
    private Period period;

    public Activity(Long id, ActivityType activityType, String description, User user, List<PeriodActivity> periodActivityList, Period period) {
        this.id = id;
        this.activityType = activityType;
        this.description = description;
        this.user = user;
        this.periodActivityList = periodActivityList;
        this.period = period;
    }

    public Activity (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PeriodActivity> getPeriodActivityList() {
        if(this.periodActivityList == null){
            this.periodActivityList = new ArrayList<>();
        }
        return periodActivityList;
    }

    public void setPeriodActivityList(List<PeriodActivity> periodActivityList) {
        this.periodActivityList = periodActivityList;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
