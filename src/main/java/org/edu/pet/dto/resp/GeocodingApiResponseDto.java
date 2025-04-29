package org.edu.pet.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.edu.pet.deserialization.CoordinateDeserializer;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponseDto(

        @JsonProperty("name")
        String city,
        @JsonProperty("lat")
        @JsonDeserialize(using = CoordinateDeserializer.class)
        BigDecimal latitude,
        @JsonProperty("lon")
        @JsonDeserialize(using = CoordinateDeserializer.class)
        BigDecimal longitude,
        @JsonProperty("country")
        String countryCode,

        String state) {

}