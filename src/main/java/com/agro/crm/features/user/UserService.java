package com.agro.crm.features.user;

import com.agro.crm.features.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(RegisterRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        user.setRoles(req.getRoles());

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
