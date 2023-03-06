package com.lena.application.service.impl;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.model.entity.City;
import com.lena.application.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private CityServiceImpl cityService;
    private City city;

    @BeforeEach
    void init() {
        city = createCity(1L, "Tokyo", "Photo_1");
    }

    @Test
    void getCity_cityExist_returnCity() {
        City expected = createCity(1L, "Tokyo", "Photo_1");
        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(city));

        var actualCity = cityService.getCity("tokyo");

        assertEquals(expected, actualCity);

        verify(cityRepository).findCityByNameIgnoreCase("tokyo");
    }

    @Test
    void getCity_cityNotExist_returnException() {
        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                cityService.getCity("delhi"));

        assertEquals("City with such name not found", exception.getMessage());
    }

    @Test
    void editNameCity_correctData_updateCity() {
        CityEditRequest request = new CityEditRequest();
        request.setName("Berlin");

        City expected = createCity(1L, "Berlin", "Photo_1");

        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(city));

        var actualCity = cityService.editCity(city.getName(), request);

        verify(cityRepository).save(expected);
        assertEquals(expected, actualCity);
    }

    @Test
    void editPhotoCity_correctData_updateCity() {
        CityEditRequest request = new CityEditRequest();
        request.setPhoto("NewPhoto");

        City expected = createCity(1L, "Tokyo", "NewPhoto");

        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(city));

        var actualCity = cityService.editCity(city.getName(), request);

        verify(cityRepository).save(expected);
        assertEquals(expected, actualCity);
    }

    @Test
    void getAllCities_pageSizeLessZero_ReturnAll() {
        List<City> cityList = List.of(city, createCity(2L, "Berlin", "Photo_2"));

        when(cityRepository.findAll()).thenReturn(cityList);

        var actualList = cityService.getAllCities(1, -1);

        assertEquals(cityList, actualList);
        verify(cityRepository).findAll();
    }

    @Test
    void getAllCities_pageSizeZeroOrMoreThanZero_ReturnPage() {
        Pageable pageable = PageRequest.of(1, 1);
        List<City> cities = List.of(city, createCity(2L, "Berlin", "Photo_2"));

        when(cityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(cities));

        var actualList = cityService.getAllCities(2, 1);

        assertEquals(cities, actualList);
        verify(cityRepository).findAll(pageable);
    }

    private City createCity(Long id, String name, String photo) {
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setPhoto(photo);
        return city;
    }
}
