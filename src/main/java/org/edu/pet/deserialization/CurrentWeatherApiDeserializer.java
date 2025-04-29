package org.edu.pet.deserialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.edu.pet.dto.resp.CurrentWeatherApiResponseDto;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CurrentWeatherApiDeserializer extends StdDeserializer<CurrentWeatherApiResponseDto> {

    public CurrentWeatherApiDeserializer() {
        super(CurrentWeatherApiResponseDto.class);
    }

    @Override
    public CurrentWeatherApiResponseDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode rootNode = p.getCodec().readTree(p);
        JsonNode weatherNode = (rootNode.get("weather")).get(0);
        JsonNode mainNode = rootNode.get("main");
        JsonNode sysNode = rootNode.get("sys");

        String iconCode = weatherNode.get("icon").asText();
        double temperature = mainNode.get("temp").asDouble();
        String weatherDescription = StringUtils.capitalize(weatherNode.get("description").asText());
        double feelLike = mainNode.get("feels_like").asDouble();
        String countryCode = sysNode.get("country").asText();
        int humidity = mainNode.get("humidity").asInt();

        return new CurrentWeatherApiResponseDto(
                iconCode,
                temperature,
                weatherDescription,
                feelLike,
                countryCode,
                humidity);
    }
}