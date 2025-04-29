package org.edu.pet.mapper;

import org.edu.pet.dto.resp.CurrentWeatherApiResponseDto;
import org.edu.pet.dto.resp.WeatherCardDto;
import org.edu.pet.model.Location;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface WeatherDtoMapper {

    WeatherCardDto toDto(CurrentWeatherApiResponseDto currentWeatherApiResponseDto, Location location);
}
