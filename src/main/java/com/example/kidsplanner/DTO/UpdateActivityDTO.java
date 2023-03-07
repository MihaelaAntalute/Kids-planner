package com.example.kidsplanner.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UpdateActivityDTO {

    private String description;

    @JsonCreator
    public UpdateActivityDTO(String description) {
        this.description = description;
    }

    public UpdateActivityDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
