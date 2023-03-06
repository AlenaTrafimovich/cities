package com.lena.application.service;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.model.entity.City;

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
    List<City> getAllCities(int page, int pageSize);

    /**
     * Get city by name
     *
     * @param name of the city
     * @return {@link City} information about the city
     */
    City getCity(String name);

    /**
     * Edit city
     *
     * @param name    of the city
     * @param request data to update
     * @return {@link City} information about the city
     */
    City editCity(String name, CityEditRequest request);
}
