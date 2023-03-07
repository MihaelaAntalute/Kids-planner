package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private String username;

    @Column
    private LocalDate birthday;

    @Column
    private String email;

    @Column
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-period")
    private List<Period> period;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-activity")
    private List<Activity> activityList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-wish")
    private List<Wish> wishList;

    @ManyToMany
    @JsonIgnoreProperties("userList")
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList;
  public User(){}

    public User(Long id, String username, LocalDate birthday, String email, String password, List<Period> period, List<Activity> activityList, List<Wish> wishList, List<Role> roleList) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.period = period;
        this.activityList = activityList;
        this.wishList = wishList;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailException{
        if(!email.contains("@")){
            throw new InvalidEmailException("Your email is not valid");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Period> getPeriod() {
      if(this.period == null){
          this.period = new ArrayList<>();
      }
        return period;
    }

    public void setPeriod(List<Period> period) {
        this.period = period;
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

    public List<Wish> getWishList() {
      if(this.wishList == null){
          this.wishList = new ArrayList<>();
      }
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
    }

    public List<Role> getRoleList() {
      if (this.roleList == null) {
        this.roleList = new ArrayList<>();
    }
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

}
