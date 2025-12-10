package com.checkmate.checkmate_backend.modules.jobs.service;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobStatus;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingResponse;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingSaveRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobPostingService {

    JobPostingResponse saveJobForUser(Long userId, JobPostingSaveRequest request);

    List<JobPostingResponse> getSavedJobsForUser(Long userId);

    JobPostingResponse getJobForUser(Long userId, Long jobId);

    Page<JobPostingResponse> getSavedJobsForUser(
            Long userId,
            String query,
            String location,
            String tag,
            JobStatus status,
            Pageable pageable
    );
}
