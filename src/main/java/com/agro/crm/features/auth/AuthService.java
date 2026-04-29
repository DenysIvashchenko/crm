package com.agro.crm.features.auth;

import com.agro.crm.core.security.JwtService;
import com.agro.crm.features.auth.dto.AuthResponse;
import com.agro.crm.features.auth.dto.LoginRequest;
import com.agro.crm.features.user.Role;
import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserDto;
import com.agro.crm.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid password");
        }
        String roles = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.joining(","));

        String token = jwtService.generateToken(user.getEmail(), user.getUsername(), roles);
        return AuthResponse.builder()
                .token(token)
                .user(UserDto.from(user))
                .build();
    }
}
