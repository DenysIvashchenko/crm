package com.agro.crm.features.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
