package com.agro.crm.features.user.dto;

import com.agro.crm.features.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {
    @NotBlank(message = "Username is mandatory")
    private String userName;

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    private String password;

    private Set<Role> roles;
}
