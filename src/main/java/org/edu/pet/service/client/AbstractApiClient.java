package org.edu.pet.service.client;

import lombok.RequiredArgsConstructor;
import org.edu.pet.exception.ApiServerException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public abstract class AbstractApiClient {

    final HttpClient httpClient;
    final String baseUrl;

    @Value("${api.key}")
    String apiKey;

    <T> HttpResponse<T> getResponse(HttpRequest req, HttpResponse.BodyHandler<T> responseBodyHandler) {

        try {
            return httpClient.send(req, responseBodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new ApiServerException();
        }
    }
}