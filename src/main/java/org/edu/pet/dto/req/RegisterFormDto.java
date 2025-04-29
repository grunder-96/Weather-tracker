package org.edu.pet.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterFormDto(
        @NotBlank(message = "The field cannot be blank")
        @Email(message = "Please enter an email in the correct format")
        @Size(min = 6, message = "Email must be at least {min} characters long")
        @Size(max = 64, message = "Email must be no more than {max} characters long")
        String login,

        @NotBlank(message = "The field cannot be blank")
        @Size(min = 8, message = "Password must be at least {min} characters long")
        @Size(max = 20, message = "Password must be no more than {max} characters long")
        @Pattern(regexp = "\\S+", message = "Password must not contain spaces")
        @Pattern(regexp = "[a-zA-Z0-9\\p{Punct}]+",
                message = "Password must contain only english letters, numbers and special characters")
        String pass,

        String passConfirm
) {

}