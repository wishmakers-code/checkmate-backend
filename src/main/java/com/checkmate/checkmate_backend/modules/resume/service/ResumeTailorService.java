package com.checkmate.checkmate_backend.modules.resume.service;

import com.checkmate.checkmate_backend.modules.resume.dto.TailoredResumeResponse;

public interface ResumeTailorService {

    /**
     * Generate a tailored resume for the given user and job posting.
     */
    TailoredResumeResponse tailorResumeForJob(Long userId, Long jobPostingId);
}
