package com.agro.crm.features.user.dto;

import com.agro.crm.features.user.Role;
import com.agro.crm.features.user.User;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserDto {

    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private Set<Role> roles;
    private LocalDateTime createdAt;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .build();
    }
}