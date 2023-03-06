package com.lena.application.controller;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.model.entity.City;
import com.lena.application.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "-1") int pageSize) {
        return new ResponseEntity<>(service.getAllCities(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<City> getCity(@PathVariable("name") String name) {
        return new ResponseEntity<>(service.getCity(name), HttpStatus.OK);
    }

    @PutMapping("/{name}")
    @Secured("ROLE_ALLOW_EDIT")
    public ResponseEntity<City> editCity(@PathVariable("name") String name,
                                         @Valid @RequestBody CityEditRequest request) {
        return new ResponseEntity<>(service.editCity(name, request), HttpStatus.OK);
    }
}
