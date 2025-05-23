package org.edu.pet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.edu.pet.dto.resp.GeocodingApiResponseDto;
import org.edu.pet.dto.resp.CurrentWeatherApiResponseDto;
import org.edu.pet.exception.ApiClientException;
import org.edu.pet.exception.ApiServerException;
import org.edu.pet.service.client.CurrentWeatherApiClient;
import org.edu.pet.service.client.GeocodingApiClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenWeatherApiService {

    private final GeocodingApiClient geocodingApiClient;
    private final CurrentWeatherApiClient currentWeatherApiClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JsonProcessingException.class)
    public List<GeocodingApiResponseDto> getLocationsByKeyword(String keyword) {

        HttpResponse<String> response = geocodingApiClient.findLocationsByKeyword(keyword);
        validateResponse(response);

        return objectMapper.readValue(response.body(), new TypeReference<List<GeocodingApiResponseDto>>() {});
    }

    @SneakyThrows(JsonProcessingException.class)
    public CurrentWeatherApiResponseDto getWeatherByCoordinates(BigDecimal latitude, BigDecimal longitude) {

        HttpResponse<String> response = currentWeatherApiClient.getWeatherByCoordinates(latitude, longitude);
        validateResponse(response);

        return objectMapper.readValue(response.body(), CurrentWeatherApiResponseDto.class);
    }

    private void validateResponse(HttpResponse<?> response) {

        if (response.statusCode() / 100 == 4) {
            throw new ApiClientException();
        }

        if (response.statusCode() / 100 == 5) {
            throw new ApiServerException();
        }
    }
}