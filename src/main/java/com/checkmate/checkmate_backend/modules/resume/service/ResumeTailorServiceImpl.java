package com.checkmate.checkmate_backend.modules.resume.service;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.jobs.repository.JobPostingRepository;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.resume.ai.AiClient;
import com.checkmate.checkmate_backend.modules.resume.domain.TailoredResume;
import com.checkmate.checkmate_backend.modules.resume.dto.TailoredResumeResponse;
import com.checkmate.checkmate_backend.modules.resume.repository.TailoredResumeRepository;
import com.checkmate.checkmate_backend.modules.masterprofile.service.MasterProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class ResumeTailorServiceImpl implements ResumeTailorService {

    private final JobPostingRepository jobPostingRepository;
    private final TailoredResumeRepository tailoredResumeRepository;
    private final MasterProfileService masterProfileService;
    private final AiClient aiClient;

    public ResumeTailorServiceImpl(JobPostingRepository jobPostingRepository,
                                   TailoredResumeRepository tailoredResumeRepository,
                                   MasterProfileService masterProfileService,
                                   AiClient aiClient) {
        this.jobPostingRepository = jobPostingRepository;
        this.tailoredResumeRepository = tailoredResumeRepository;
        this.masterProfileService = masterProfileService;
        this.aiClient = aiClient;
    }

    @Override
    public TailoredResumeResponse tailorResumeForJob(Long userId, Long jobPostingId) {
        // 1) Ensure the job belongs to this user
        JobPosting job = jobPostingRepository.findByIdAndUserId(jobPostingId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Job not found for user. jobId=%d, userId=%d".formatted(jobPostingId, userId)
                ));

        // 2) Load the user's master profile
        // Adjust this call to match your actual MasterProfileService API
        MasterProfile masterProfile = masterProfileService.getOrCreateMasterProfileForUser(userId);
        

        // 3) Call AI client to generate structured resume JSON
        Map<String, Object> resumeJson = aiClient.generateTailoredResume(masterProfile, job);

        // 4) Persist TailoredResume
        TailoredResume entity = new TailoredResume();
        entity.setUser(job.getUser());
        entity.setJobPosting(job);
        entity.setMasterProfile(masterProfile);
        entity.setResumeJson(resumeJson);

        TailoredResume saved = tailoredResumeRepository.save(entity);

        // 5) Map to DTO
        return toResponse(saved);
    }

    private TailoredResumeResponse toResponse(TailoredResume entity) {
        TailoredResumeResponse dto = new TailoredResumeResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setJobPostingId(entity.getJobPosting().getId());
        dto.setMasterProfileId(entity.getMasterProfile().getId());
        dto.setResumeJson(entity.getResumeJson());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
