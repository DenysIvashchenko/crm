package com.agro.crm.features.user;

import com.agro.crm.features.auth.dto.RegisterRequest;
import com.agro.crm.features.user.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserDto updateUser(Long id, RegisterRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        user.setRoles(req.getRoles());

        return UserDto.from(userRepository.save(user));
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
        return UserDto.from(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::from).toList();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
