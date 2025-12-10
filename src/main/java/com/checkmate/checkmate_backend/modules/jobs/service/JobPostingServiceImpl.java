package com.checkmate.checkmate_backend.modules.jobs.service;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.jobs.domain.JobStatus;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingResponse;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingSaveRequest;
import com.checkmate.checkmate_backend.modules.jobs.repository.JobPostingRepository;
import com.checkmate.checkmate_backend.modules.user.domain.User;
import com.checkmate.checkmate_backend.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    public JobPostingServiceImpl(JobPostingRepository jobPostingRepository,
                                 UserRepository userRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JobPostingResponse saveJobForUser(Long userId, JobPostingSaveRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        JobPosting job = findExistingJobForUser(userId, request)
                .orElseGet(JobPosting::new);

        if (job.getId() == null) {
            job.setUser(user);
        }

        job.setExternalId(request.getExternalId());
        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSource(request.getSource());
        job.setJobUrl(request.getJobUrl());
        job.setRawPostingJson(request.getRawPostingJson());
        job.setNormalizedTags(request.getNormalizedTags());

        if (request.getStatus() != null) {
            job.setStatus(request.getStatus());
        }
        if (request.getNotes() != null) {
            job.setNotes(request.getNotes());
        }

        JobPosting saved = jobPostingRepository.save(job);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostingResponse> getSavedJobsForUser(Long userId) {
        return jobPostingRepository.findByUserIdOrderBySavedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostingResponse getJobForUser(Long userId, Long jobId) {
        JobPosting job = jobPostingRepository.findByIdAndUserId(jobId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Job not found for user. jobId=%d, userId=%d".formatted(jobId, userId)
                ));
        return toResponse(job);
    }

    private Optional<JobPosting> findExistingJobForUser(Long userId, JobPostingSaveRequest request) {
        if (request.getExternalId() != null && !request.getExternalId().isBlank()) {
            Optional<JobPosting> byExternalId =
                    jobPostingRepository.findByUserIdAndExternalId(userId, request.getExternalId());
            if (byExternalId.isPresent()) {
                return byExternalId;
            }
        }

        if (request.getJobUrl() != null && !request.getJobUrl().isBlank()) {
            Optional<JobPosting> byJobUrl =
                    jobPostingRepository.findByUserIdAndJobUrl(userId, request.getJobUrl());
            if (byJobUrl.isPresent()) {
                return byJobUrl;
            }
        }

        return Optional.empty();
    }

    private JobPostingResponse toResponse(JobPosting job) {
        JobPostingResponse dto = new JobPostingResponse();
        dto.setId(job.getId());
        dto.setExternalId(job.getExternalId());
        dto.setTitle(job.getTitle());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setSource(job.getSource());
        dto.setJobUrl(job.getJobUrl());
        dto.setRawPostingJson(job.getRawPostingJson());
        dto.setNormalizedTags(job.getNormalizedTags());
        dto.setStatus(job.getStatus());
        dto.setNotes(job.getNotes());
        dto.setSavedAt(job.getSavedAt());
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobPostingResponse> getSavedJobsForUser(
            Long userId,
            String query,
            String location,
            String tag,
            JobStatus status,
            Pageable pageable
    ) {
        List<JobPosting> all = jobPostingRepository.findByUserIdOrderBySavedAtDesc(userId);

        // In-memory filtering for now
        List<JobPosting> filtered = all.stream()
                .filter(job -> {
                    if (query != null && !query.isBlank()) {
                        String q = query.toLowerCase();
                        boolean matchesTitle = job.getTitle() != null && job.getTitle().toLowerCase().contains(q);
                        boolean matchesCompany = job.getCompany() != null && job.getCompany().toLowerCase().contains(q);
                        return matchesTitle || matchesCompany;
                    }
                    return true;
                })
                .filter(job -> {
                    if (location != null && !location.isBlank()) {
                        return job.getLocation() != null
                                && job.getLocation().toLowerCase().contains(location.toLowerCase());
                    }
                    return true;
                })
                .filter(job -> {
                    if (status != null) {
                        return job.getStatus() == status;
                    }
                    return true;
                })
                .filter(job -> {
                    if (tag != null && !tag.isBlank()) {
                        if (job.getNormalizedTags() == null) {
                            return false;
                        }
                        String t = tag.toLowerCase();
                        return job.getNormalizedTags().stream()
                                .anyMatch(existing -> existing != null
                                        && existing.toLowerCase().contains(t));
                    }
                    return true;
                })
                .toList();

        int total = filtered.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), total);

        List<JobPostingResponse> pageContent = filtered.subList(
                        Math.min(start, end),
                        end
                ).stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(pageContent, pageable, total);
    }
}
