package com.lena.application.controller;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.controller.dto.CityResponse;
import com.lena.application.enums.StatusCode;
import com.lena.application.service.CityService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/cities")
@Tag(name = "Cities", description = "Service for searching, getting and editing cities")
public class CityController {
    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = StatusCode.OK, description = "OK",
                    content = @Content(schema = @Schema(implementation = CityResponse.class)))
    })
    public ResponseEntity<List<CityResponse>> getAllCities(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "-1") int pageSize) {
        return new ResponseEntity<>(service.getAllCities(page, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = StatusCode.OK, description = "OK",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @ApiResponse(responseCode = StatusCode.NOT_FOUND, description = "The resource you were trying to reach is not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<CityResponse> getCity(@PathVariable("name") String name) {
        return new ResponseEntity<>(service.getCityByName(name), HttpStatus.OK);
    }

    @PutMapping(value = "/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = StatusCode.OK, description = "OK",
                    content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @ApiResponse(responseCode = StatusCode.BAD_REQUEST, description = "Received incorrect data",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = StatusCode.NOT_FOUND, description = "The resource you were trying to reach is not found",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    public ResponseEntity<CityResponse> editCity(@PathVariable("name") String name,
                                                 @Valid @RequestBody CityEditRequest request) {
        log.info("Updating city {} with values {}", name, request);
        return new ResponseEntity<>(service.editCity(name, request), HttpStatus.OK);
    }
}
