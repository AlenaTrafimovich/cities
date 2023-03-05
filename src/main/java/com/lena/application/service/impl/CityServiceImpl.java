package com.lena.application.service.impl;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.model.entity.City;
import com.lena.application.repository.CityRepository;
import com.lena.application.service.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAllCities(int page, int pageSize) {
        if (pageSize < 0) {
            return cityRepository.findAll();
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<City> cities = cityRepository.findAll(pageable);
        return cities.toList();
    }

    @Override
    public City getCityByName(String name) {
        return cityRepository.findCityByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("City with such name not found"));
    }

    @Override
    public City editCity(String name, CityEditRequest request) {
        City city = getCityByName(name);
        city.setName(request.getName());
        city.setPhoto(request.getPhoto());
        cityRepository.save(city);
        return city;
    }
}
