package com.example.kidsplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq",
            sequenceName = "category_seq",
            initialValue = 1,
            allocationSize = 1)
    private Long id;

    @Column
    private RoleType roleType;


    @ManyToMany(mappedBy = "roleList")
    @JsonIgnoreProperties("roleList")
    private List<User> userList;

    public Role(){}

    public Role(Long id, RoleType roleType, List<User> userList) {
        this.id = id;
        this.roleType = roleType;
        this.userList = userList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<User> getUserList() {
        if (userList == null){
            userList = new ArrayList<>();
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
