package org.edu.pet.dto.resp;

import java.math.BigDecimal;

public record WeatherCardDto(

        String name,
        String iconCode,
        double temperature,
        String weatherDescription,
        double feelsLike,
        String countryCode,
        int humidity,
        BigDecimal latitude,
        BigDecimal longitude
) {

}