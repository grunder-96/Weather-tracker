package org.edu.pet.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthUserDto(
    @NotBlank(message = "The field cannot be blank")
    @Email(message = "Please enter an email in the correct format")
    String login,

    @NotBlank(message = "The field cannot be blank")
    String pass
) {

}
