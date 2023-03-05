package com.lena.application.controller.dto;

import jakarta.validation.constraints.Pattern;

public class CityEditRequest {
    @Pattern(regexp = "[\\w-]+")
    private String name;
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
