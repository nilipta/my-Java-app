package com.springapp.myapp.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Name is required")
    @Max(value=255, message="Name must be less tha 255")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid!")
    private String email;
}
