package org.edu.pet.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.edu.pet.deserialization.CurrentWeatherApiDeserializer;

@JsonDeserialize(using = CurrentWeatherApiDeserializer.class)
public record CurrentWeatherApiResponseDto(

        String iconCode,
        double temperature,
        String weatherDescription,
        double feelsLike,
        String countryCode,
        int humidity) {

}