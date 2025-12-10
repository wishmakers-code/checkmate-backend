package com.checkmate.checkmate_backend.modules.resume.ai;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;

import java.util.Map;

public interface AiClient {

    /**
     * Generate a tailored resume JSON given a master profile and a job posting.
     * The returned map should be a structured JSON tree that the frontend can render.
     */
    Map<String, Object> generateTailoredResume(MasterProfile masterProfile, JobPosting jobPosting);
}
