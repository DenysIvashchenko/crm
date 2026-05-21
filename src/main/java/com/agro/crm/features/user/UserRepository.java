package com.agro.crm.features.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    List<User> findAllByRolesContaining(Role role);

    boolean existsByEmail(String email);

}
