package com.checkmate.checkmate_backend.modules.jobs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByUserIdOrderBySavedAtDesc(Long userId);

    Optional<JobPosting> findByIdAndUserId(Long id, Long userId);

    Optional<JobPosting> findByUserIdAndExternalId(Long userId, String externalId);

    Optional<JobPosting> findByUserIdAndJobUrl(Long userId, String jobUrl);
}