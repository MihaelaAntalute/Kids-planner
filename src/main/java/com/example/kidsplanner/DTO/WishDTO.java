package com.example.kidsplanner.DTO;

public class WishDTO {
    private String username;
    private String text;

    public WishDTO(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public WishDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}