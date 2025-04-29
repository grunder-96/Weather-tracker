package org.edu.pet.service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class GeocodingApiClient extends AbstractApiClient {

    public GeocodingApiClient(HttpClient httpClient, @Value("${api.geocoding.url}") String baseUrl) {
        super(httpClient, baseUrl);
    }

    public  HttpResponse<String> findLocationsByKeyword(String keyword) {

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("q", keyword)
                .queryParam("appid", apiKey)
                .queryParam("limit", 5)
                .build()
                .toUri();

        HttpRequest req = HttpRequest.newBuilder(uri).build();

        return getResponse(req, HttpResponse.BodyHandlers.ofString());
    }
}