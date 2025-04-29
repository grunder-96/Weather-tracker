package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.resp.CurrentWeatherApiResponseDto;
import org.edu.pet.dto.resp.WeatherCardDto;
import org.edu.pet.mapper.WeatherDtoMapper;
import org.edu.pet.model.Location;
import org.edu.pet.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherCardService {

    private final OpenWeatherApiService openWeatherApiService;
    private final UserLocationService userLocationService;

    private final WeatherDtoMapper weatherDtoMapper;

    @Transactional(readOnly = true)
    public List<WeatherCardDto> getWeatherCards(User user) {

        List<Location> locations = userLocationService.findAllInternal(user);
        List<WeatherCardDto> weatherCards = new ArrayList<>();

        for (Location location : locations) {
            CurrentWeatherApiResponseDto apiResponse = openWeatherApiService
                    .getWeatherByCoordinates(location.getLatitude(), location.getLongitude());
            WeatherCardDto dto = weatherDtoMapper.toDto(apiResponse, location);
            weatherCards.add(dto);
        }

        return weatherCards;
    }
}