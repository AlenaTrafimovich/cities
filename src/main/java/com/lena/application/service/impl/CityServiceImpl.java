package com.lena.application.service.impl;

import com.lena.application.controller.dto.CityEditRequest;
import com.lena.application.controller.dto.CityResponse;
import com.lena.application.exception.ResourceNotFoundException;
import com.lena.application.mapper.CityMapper;
import com.lena.application.model.entity.City;
import com.lena.application.repository.CityRepository;
import com.lena.application.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityResponse> getAllCities(int page, int pageSize) {
        if (pageSize < 0) {
            return cityRepository.findAll().stream()
                    .map(cityMapper::toCityResponse)
                    .collect(Collectors.toList());
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<City> cities = cityRepository.findAll(pageable);
        return cities.map(cityMapper::toCityResponse).stream()
                .collect(Collectors.toList());
    }

    @Override
    public CityResponse getCityByName(String name) {
        return cityMapper.toCityResponse(getCity(name));
    }

    @Override
    public CityResponse editCity(String name, CityEditRequest request) {
        City city = getCity(name);
        if (request.getName() != null) {
            city.setName(request.getName());
        }
        if (request.getPhoto() != null) {
            city.setPhoto(request.getPhoto());
        }
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            log.error("Unexpected exception while trying to update city {}", city, e);
        }
        return cityMapper.toCityResponse(city);
    }

    private City getCity(String name) {
        log.info("Fetching city by name {}", name);
        return cityRepository.findCityByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("City with such name not found"));
    }
}
