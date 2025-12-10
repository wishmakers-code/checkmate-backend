package com.checkmate.checkmate_backend.modules.applications.service;


import com.checkmate.checkmate_backend.modules.applications.domain.Application;
import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatus;
import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatusHistory;
import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationResponse;
import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationStatusUpdateRequest;
import com.checkmate.checkmate_backend.modules.applications.dto.CreateApplicationRequest;
import com.checkmate.checkmate_backend.modules.applications.repository.ApplicationRepository;
import com.checkmate.checkmate_backend.modules.applications.repository.ApplicationStatusHistoryRepository;
import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.jobs.repository.JobPostingRepository;
import com.checkmate.checkmate_backend.modules.resume.domain.TailoredResume;
import com.checkmate.checkmate_backend.modules.resume.repository.TailoredResumeRepository;
import com.checkmate.checkmate_backend.modules.user.domain.User;
import com.checkmate.checkmate_backend.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusHistoryRepository statusHistoryRepository;
    private final JobPostingRepository jobPostingRepository;
    private final TailoredResumeRepository tailoredResumeRepository;
    private final UserRepository userRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationStatusHistoryRepository statusHistoryRepository,
                                  JobPostingRepository jobPostingRepository,
                                  TailoredResumeRepository tailoredResumeRepository,
                                  UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.tailoredResumeRepository = tailoredResumeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApplicationResponse createApplication(Long userId, CreateApplicationRequest request) {
        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Validate job belongs to user
        JobPosting job = jobPostingRepository.findByIdAndUserId(request.getJobPostingId(), userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Job not found for user. jobId=%d, userId=%d".formatted(request.getJobPostingId(), userId)
                ));

        TailoredResume tailoredResume = null;
        if (request.getTailoredResumeId() != null) {
            tailoredResume = tailoredResumeRepository.findById(request.getTailoredResumeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tailored resume not found: " + request.getTailoredResumeId()
                    ));

            if (!tailoredResume.getUser().getId().equals(userId) ||
                !tailoredResume.getJobPosting().getId().equals(job.getId())) {
                throw new IllegalArgumentException("Tailored resume does not belong to this user/job.");
            }
        }

        ApplicationStatus initialStatus =
                request.getStatus() != null ? request.getStatus() : ApplicationStatus.INTERESTED;

        Application app = new Application();
        app.setUser(user);
        app.setJobPosting(job);
        app.setTailoredResume(tailoredResume);
        app.setStatus(initialStatus);
        app.setNotes(request.getNotes());

        if (initialStatus == ApplicationStatus.APPLIED) {
            app.setAppliedAt(OffsetDateTime.now());
        }

        Application saved = applicationRepository.save(app);

        // Status history entry
        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(saved);
        history.setOldStatus(null);
        history.setNewStatus(initialStatus);
        history.setComment("Initial status");
        statusHistoryRepository.save(history);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsForUser(Long userId) {
        List<Application> apps = applicationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return apps.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(Long userId,
                                                       Long applicationId,
                                                       ApplicationStatusUpdateRequest request) {
        Application app = applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Application not found for user. appId=%d, userId=%d".formatted(applicationId, userId)
                ));

        ApplicationStatus oldStatus = app.getStatus();
        ApplicationStatus newStatus = request.getNewStatus();

        if (newStatus == null) {
            throw new IllegalArgumentException("newStatus is required");
        }

        if (oldStatus == newStatus) {
            // no-op, but you could still record a history entry if you want
            return toResponse(app);
        }

        app.setStatus(newStatus);
        if (newStatus == ApplicationStatus.APPLIED && app.getAppliedAt() == null) {
            app.setAppliedAt(OffsetDateTime.now());
        }

        Application saved = applicationRepository.save(app);

        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(saved);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setComment(request.getComment());
        statusHistoryRepository.save(history);

        return toResponse(saved);
    }

    private ApplicationResponse toResponse(Application app) {
        ApplicationResponse dto = new ApplicationResponse();
        dto.setId(app.getId());
        dto.setUserId(app.getUser().getId());
        dto.setJobPostingId(app.getJobPosting().getId());
        dto.setTailoredResumeId(
                app.getTailoredResume() != null ? app.getTailoredResume().getId() : null
        );

        dto.setJobTitle(app.getJobPosting().getTitle());
        dto.setCompany(app.getJobPosting().getCompany());
        dto.setLocation(app.getJobPosting().getLocation());

        dto.setStatus(app.getStatus());
        dto.setNotes(app.getNotes());
        dto.setAppliedAt(app.getAppliedAt());
        dto.setCreatedAt(app.getCreatedAt());
        dto.setUpdatedAt(app.getUpdatedAt());

        return dto;
    }
}
