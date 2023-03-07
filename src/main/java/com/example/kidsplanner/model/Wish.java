package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "period_Id")
    @JsonBackReference(value = "wish-period")
    private Period period;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonBackReference(value = "user-wish")
    private User user;

    public Wish(Long id, String text, Period period,User user) {
        this.id = id;
        this.text = text;
        this.period = period;
        this.user = user;
    }

    public Wish(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}