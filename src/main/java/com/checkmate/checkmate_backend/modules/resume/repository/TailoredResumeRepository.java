package com.checkmate.checkmate_backend.modules.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.resume.domain.TailoredResume;

import java.util.List;

public interface TailoredResumeRepository extends JpaRepository<TailoredResume, Long> {

    List<TailoredResume> findByUserIdAndJobPostingIdOrderByCreatedAtDesc(Long userId, Long jobPostingId);
}
