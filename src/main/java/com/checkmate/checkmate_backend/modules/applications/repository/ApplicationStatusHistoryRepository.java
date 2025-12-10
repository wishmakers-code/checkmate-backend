package com.checkmate.checkmate_backend.modules.applications.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatusHistory;

import java.util.List;

public interface ApplicationStatusHistoryRepository
        extends JpaRepository<ApplicationStatusHistory, Long> {

    List<ApplicationStatusHistory> findByApplicationIdOrderByChangedAtAsc(Long applicationId);
}
