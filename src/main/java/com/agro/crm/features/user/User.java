package com.agro.crm.features.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", unique = true)
    private String userName;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    public static User create(
            String userName,
            String fullName,
            String email,
            String encodedPassword,
            Set<Role> roles
    ) {
        User user = new User();
        user.userName = userName;
        user.fullName = fullName;
        user.email = email;
        user.password = encodedPassword;
        user.roles.addAll(roles);

        return user;
    }

    public void updateProfile(
            String userName,
            String fullName,
            String email
    ) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public void changePassword(
            String encodedPassword
    ) {
        this.password = encodedPassword;
    }

    public void replaceRoles(
            Set<Role> roles
    ) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

}
