package org.edu.pet.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CurrentWeatherApiClient extends AbstractApiClient {

    public CurrentWeatherApiClient(HttpClient httpClient, @Value("${api.current_weather.url}") String baseUrl) {
        super(httpClient, baseUrl);
    }

    public HttpResponse<String> getWeatherByCoordinates(BigDecimal latitude, BigDecimal longitude) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build()
                .toUri();

        HttpRequest req = HttpRequest.newBuilder(uri).build();

        return getResponse(req, HttpResponse.BodyHandlers.ofString());
    }
}