package com.agro.crm.features.user;

import com.agro.crm.features.auth.dto.RegisterRequest;
import com.agro.crm.features.user.dto.UserDto;
import com.agro.crm.features.user.dto.UserUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody RegisterRequest req) {
        return userService.createUser(req);
    }

    @GetMapping
    public List<UserDto> list() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto req) {
        return userService.updateUser(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
