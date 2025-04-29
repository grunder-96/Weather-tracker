package org.edu.pet.dto.resp;

import java.math.BigDecimal;

public record LocationCardDto(

        String city,
        BigDecimal latitude,
        BigDecimal longitude,
        String countryCode,
        String state,
        boolean isAdded) {

}