package com.lena.application.service;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.controller.dto.CityResponse;

import java.util.List;

/**
 * Service for searching, getting and editing cities
 */
public interface CityService {

    /**
     * Get list of all cities
     *
     * @param page     the page of search
     * @param pageSize the amount of elements on the page
     * @return list of all cities
     */
    List<CityResponse> getAllCities(int page, int pageSize);

    /**
     * Get city by name
     *
     * @param name of the city
     * @return {@link CityResponse} information about the city
     */
    CityResponse getCityByName(String name);

    /**
     * Edit city
     *
     * @param name    of the city
     * @param request data to update
     * @return {@link CityResponse} information about the city
     */
    CityResponse editCity(String name, CityEditRequest request);
}
