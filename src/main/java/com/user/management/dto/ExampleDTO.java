package com.user.management.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExampleDTO {
    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 100, message = "The length of first name must be between 2 and 100 characters.")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 2, max = 100, message = "The length of first name must be between 2 and 100 characters.")
    private String lastName;
}
