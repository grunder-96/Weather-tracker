package org.edu.pet.dto.req;

import java.math.BigDecimal;

public record AddLocationDto(

    String name,
    BigDecimal latitude,
    BigDecimal longitude
) {

}