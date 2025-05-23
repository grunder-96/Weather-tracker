package org.edu.pet.service;

import org.edu.pet.config.AppConfig;
import org.edu.pet.config.HibernateConfig;
import org.edu.pet.config.TestHibernateConfig;
import org.edu.pet.dto.resp.GeocodingApiResponseDto;
import org.edu.pet.exception.ApiClientException;
import org.edu.pet.service.client.CurrentWeatherApiClient;
import org.edu.pet.service.client.GeocodingApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.net.http.HttpResponse;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringJUnitConfig(classes = {AppConfig.class, HibernateConfig.class, TestHibernateConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class OpenWeatherApiServiceIT {

    @MockitoBean
    private GeocodingApiClient geocodingApiClient;

    @MockitoBean
    private CurrentWeatherApiClient currentWeatherApiClient;

    @Autowired
    private OpenWeatherApiService openWeatherApiService;

    private String keyword = "Saint Petersburg";

    @Test
    void shouldReturnCorrectResultIfKeywordIsValid() {
        String body = """
                [
                    {
                        "name": "Saint Petersburg",
                        "lat": 59.938732,
                        "lon": 30.316229,
                        "country": "RU",
                        "state": "Saint Petersburg"
                    },
                    {
                        "name": "Saint Petersburg",
                        "local_names": {
                            "en": "Saint Petersburg",
                            "ru": "Сент-Питерсберг",
                            "uk": "Сент-Пітерсбург"
                        },
                        "lat": 27.7703796,
                        "lon": -82.6695085,
                        "country": "US",
                        "state": "Florida"
                    }
                ]
                """;
        doReturn(returnResponse(body, OK))
                .when(geocodingApiClient)
                .findLocationsByKeyword(keyword);

        List<GeocodingApiResponseDto> locations = openWeatherApiService.getLocationsByKeyword(keyword);

        assertThat(locations.size()).isEqualTo(2);
        assertThat(locations).map(GeocodingApiResponseDto::city)
                .contains(keyword);
        assertThat(locations).map(GeocodingApiResponseDto::state)
                .containsAll(List.of("Saint Petersburg", "Florida"));
    }

    @Test
    void shouldThrowExceptionIfKeywordIsInvalid() {
        doReturn(returnResponse("", BAD_REQUEST))
                .when(geocodingApiClient)
                .findLocationsByKeyword(any());

        assertThatThrownBy(() -> openWeatherApiService.getLocationsByKeyword(keyword))
            .isInstanceOf(ApiClientException.class);
    }

    private static HttpResponse<String> returnResponse(String body, HttpStatus status) {
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.statusCode()).thenReturn(status.value());
        when(response.body()).thenReturn(body);

        return response;
    }
}