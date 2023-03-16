package com.lena.application.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponse {

    @Schema(description = "City name")
    private String name;

    @Schema(description = "Photo of city")
    private String photo;
}
