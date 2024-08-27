package com.user.management.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateUserRequest {
    @NotBlank(message = "name is required")
    @Size(min = 3, max = 100, message = "The length of name must be between 3 and 100 characters.")
    private String name;

    @NotBlank(message = "username is required")
    @Size(min = 5, max = 100, message = "The length of username must be between 5 and 100 characters.")
    private String username;

    @NotBlank(message = "email is required")
    @Size(min = 10, max = 100, message = "The length of email must be between 10 and 100 characters.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 5, max = 100, message = "The length of password must be between 5 and 100 characters.")
    private String password;
}
