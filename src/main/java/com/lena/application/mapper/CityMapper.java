package com.lena.application.mapper;

import com.lena.application.controller.dto.CityResponse;
import com.lena.application.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityResponse toCityResponse(City city);
}
