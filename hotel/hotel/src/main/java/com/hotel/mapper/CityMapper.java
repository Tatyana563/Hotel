package com.hotel.mapper;

import com.hotel.model.dto.CityDTO;
import com.hotel.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDTO cityToCityDTO(City city);
}
