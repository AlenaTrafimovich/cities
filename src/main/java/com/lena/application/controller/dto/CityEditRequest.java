package com.lena.application.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CityEditRequest {

    @Pattern(regexp = "([\\w- ]+| )", message = "Incorrect city name")
    @Schema(description = "City name")
    private String name;

    @Schema(description = "Photo of city")
    private String photo;
}
