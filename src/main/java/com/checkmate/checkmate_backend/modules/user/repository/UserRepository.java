package com.checkmate.checkmate_backend.modules.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
