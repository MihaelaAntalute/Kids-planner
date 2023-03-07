package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Double averageEvaluation;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonBackReference(value = "user-period")
    private User user;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "wish-period")
    private List<Wish> wishList;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "period-periodActivity")
    private List<PeriodActivity> periodActivity;

    @OneToMany(mappedBy = "period", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "period-activity")
    private List<Activity> activityList;

    public Period() {
    }

    public Period(Long id, LocalDate startDate, LocalDate endDate, Double averageEvaluation, User user, List<Wish> wishList, List<PeriodActivity> periodActivity, List<Activity> activityList) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageEvaluation = averageEvaluation;
        this.user = user;
        this.wishList = wishList;
        this.periodActivity = periodActivity;
        this.activityList = activityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Wish> getWishList() {
        if(this.wishList == null){
            this.wishList = new ArrayList<>();
        }
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }

    public List<PeriodActivity> getPeriodActivityList() {
        if(this.periodActivity == null){
            this.periodActivity = new ArrayList<>();
        }
        return periodActivity;
    }

    public void setPeriodActivityList(List<PeriodActivity> periodActivity) {
        this.periodActivity=periodActivity;
    }

    public List<Activity> getActivityList() {
        if(this.activityList == null){
            this.activityList = new ArrayList<>();
        }
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<PeriodActivity> getPeriodActivity() {
        return periodActivity;
    }
}
