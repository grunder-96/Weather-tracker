package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.resp.GeocodingApiResponseDto;
import org.edu.pet.dto.resp.LocationCardDto;
import org.edu.pet.mapper.LocationDtoMapper;
import org.edu.pet.model.Location;
import org.edu.pet.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationCardService {

    private final UserLocationService userLocationService;
    private final OpenWeatherApiService openWeatherApiService;

    private final LocationDtoMapper locationDtoMapper;

    @Transactional(readOnly = true)
    public List<LocationCardDto> getLocationCards(User user, String keyword) {

        List<Location> locations = userLocationService.findAllInternal(user);
        List<GeocodingApiResponseDto> apiListResponse = openWeatherApiService.getLocationsByKeyword(keyword);

        List<LocationCardDto> locationCards = new ArrayList<>();

        for (GeocodingApiResponseDto apiResponse : apiListResponse) {

            boolean isAdded = isAdded(apiResponse, locations);
            locationCards.add(locationDtoMapper.toDto(apiResponse, isAdded));
        }

        return locationCards;
    }

    private boolean isAdded(GeocodingApiResponseDto apiResponse, List<Location> locations) {

        return locations.stream()
                .anyMatch(loc ->  {
                    return loc.getLongitude().equals(apiResponse.longitude())
                            && loc.getLatitude().equals(apiResponse.latitude());
                });
    }
}