package com.agro.crm.features.auth.dto;

import com.agro.crm.features.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank private String email;

    @Size(min = 6)
    private String password;

    private Set<Role> roles;
}