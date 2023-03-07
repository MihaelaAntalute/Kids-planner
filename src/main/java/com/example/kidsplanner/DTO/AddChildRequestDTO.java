package com.example.kidsplanner.DTO;

import com.example.kidsplanner.model.InvalidEmailException;

import java.time.LocalDate;

public class AddChildRequestDTO {


    private String name;

    private LocalDate birthday;
    private String email;
    private String role;

    private String image = "https://www.dreamstime.com/cute-boy-face-cartoon-vector-illustration-graphic-design-cute-boy-face-cartoon-image110656400";

    public AddChildRequestDTO(String name, LocalDate birthday, String email, String role, String image) {

        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.role = role;
        this.image = image;
    }

    public AddChildRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setEmail(String email) throws InvalidEmailException {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Your email is not valid");
        }
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
