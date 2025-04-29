package org.edu.pet.mapper;

import org.edu.pet.dto.req.AddLocationDto;
import org.edu.pet.dto.resp.GeocodingApiResponseDto;
import org.edu.pet.dto.resp.LocationCardDto;
import org.edu.pet.model.Location;
import org.edu.pet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LocationDtoMapper {

    LocationCardDto toDto(GeocodingApiResponseDto geocodingApiResponseDto, boolean isAdded);

    @Mapping(target = "id", ignore = true)
    Location toEntity(AddLocationDto addLocationDto, User user);
}