package com.lena.application.service.impl;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.controller.dto.CityResponse;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.mapper.CityMapperImpl;
import com.lena.application.model.entity.City;
import com.lena.application.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
    @Spy
    private CityMapperImpl cityMapper;
    private City city;

    @BeforeEach
    void init() {
        city = City.builder()
                .name("Tokyo")
                .photo("Photo_1")
                .build();
    }

    @Test
    void getCity_cityExist_returnCityResponse() {
        CityResponse expectedCityResponse = createCityResponse("Tokyo", "Photo_1");

        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(city));

        var actualCityResponse = cityService.getCityByName("tokyo");

        assertEquals(expectedCityResponse, actualCityResponse);

        verify(cityRepository).findCityByNameIgnoreCase("tokyo");
    }

    @Test
    void getCity_cityNotExist_returnException() {
        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                cityService.getCityByName("delhi"));

        assertEquals("City with such name not found", exception.getMessage());
    }

    @Test
    void editNameCity_correctData_updateCity() {
        CityEditRequest request = CityEditRequest.builder()
                .name("Berlin")
                .build();

        City expectedCity = City.builder()
                .name("Berlin")
                .photo("Photo_1")
                .build();

        CityResponse expectedCityResponse = createCityResponse("Berlin", "Photo_1");

        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(expectedCity));
        when(cityRepository.save(any())).thenReturn(expectedCity);

        var actualCityResponse = cityService.editCity(request.getName(), request);

        verify(cityRepository).save(expectedCity);
        assertEquals(expectedCityResponse, actualCityResponse);
    }

    @Test
    void editPhotoCity_correctData_updateCity() {
        CityEditRequest request = CityEditRequest.builder()
                .photo("NewPhoto")
                .build();

        City expectedCity = City.builder()
                .name("Tokyo")
                .photo("NewPhoto")
                .build();

        CityResponse expectedCityResponse = createCityResponse("Tokyo", "NewPhoto");

        when(cityRepository.findCityByNameIgnoreCase(any())).thenReturn(Optional.of(city));
        when(cityRepository.save(any())).thenReturn(expectedCity);

        var actualCityResponse = cityService.editCity(request.getName(), request);

        verify(cityRepository).save(expectedCity);
        assertEquals(expectedCityResponse, actualCityResponse);
    }

    @Test
    void getAllCities_pageSizeLessZero_ReturnAll() {
        City city1 = City.builder()
                .name("Tokyo")
                .photo("Photo_1")
                .build();

        City city2 = City.builder()
                .name("Berlin")
                .photo("Photo_2")
                .build();

        List<City> cities = List.of(city1, city2);

        CityResponse cityResponse1 = createCityResponse("Tokyo", "Photo_1");
        CityResponse cityResponse2 = createCityResponse("Berlin", "Photo_2");

        List<CityResponse> expectedResponseList = List.of(cityResponse1, cityResponse2);

        when(cityRepository.findAll()).thenReturn(cities);

        var actualResponseList = cityService.getAllCities(1, -1);

        assertEquals(expectedResponseList, actualResponseList);

        verify(cityRepository).findAll();
    }

    @Test
    void getAllCities_pageSizeZeroOrMoreThanZero_ReturnPage() {
        Pageable pageable = PageRequest.of(1, 1);

        City city1 = City.builder()
                .name("Tokyo")
                .photo("Photo_1")
                .build();

        City city2 = City.builder()
                .name("Berlin")
                .photo("Photo_2")
                .build();

        List<City> cities = List.of(city1, city2);

        CityResponse cityResponse1 = createCityResponse("Tokyo", "Photo_1");
        CityResponse cityResponse2 = createCityResponse("Berlin", "Photo_2");

        List<CityResponse> expectedResponseList = List.of(cityResponse1, cityResponse2);

        when(cityRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(cities));

        var actualResponseList = cityService.getAllCities(2, 1);

        assertEquals(expectedResponseList, actualResponseList);
        verify(cityRepository).findAll(pageable);
    }

    private CityResponse createCityResponse(String name, String photo) {
        return CityResponse.builder()
                .name(name)
                .photo(photo)
                .build();
    }
}
