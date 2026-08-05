package com.agro.crm.features.user;

import com.agro.crm.features.auth.dto.RegisterRequest;
import com.agro.crm.features.user.dto.UserDto;
import com.agro.crm.features.user.dto.UserUpdateRequestDto;
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
        User user = User.create(
                req.getUsername(),
                req.getFullName(),
                req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                req.getRoles()
        );

        return UserDto.from(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(Long id, UserUpdateRequestDto req) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.updateProfile(req.getUserName(), req.getFullName(), req.getEmail());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.changePassword(passwordEncoder.encode(req.getPassword()));
        }

        if (req.getRoles() != null) {
            user.replaceRoles(req.getRoles());
        }

        return UserDto.from(user);
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
