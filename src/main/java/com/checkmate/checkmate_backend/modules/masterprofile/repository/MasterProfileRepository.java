package com.checkmate.checkmate_backend.modules.masterprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;

import java.util.Optional;

public interface MasterProfileRepository extends JpaRepository<MasterProfile, Long> {

    Optional<MasterProfile> findByUserId(Long userId);
}
