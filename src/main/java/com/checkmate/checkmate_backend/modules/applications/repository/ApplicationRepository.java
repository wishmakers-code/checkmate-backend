package com.checkmate.checkmate_backend.modules.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.applications.domain.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Application> findByIdAndUserId(Long id, Long userId);
}
