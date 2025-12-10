package com.checkmate.checkmate_backend.modules.applications.dto;

import java.time.OffsetDateTime;

import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatus;

public class ApplicationResponse {

    private Long id;
    private Long userId;
    private Long jobPostingId;
    private Long tailoredResumeId;

    private String jobTitle;
    private String company;
    private String location;

    private ApplicationStatus status;
    private String notes;

    private OffsetDateTime appliedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters & setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getJobPostingId() { return jobPostingId; }

    public void setJobPostingId(Long jobPostingId) { this.jobPostingId = jobPostingId; }

    public Long getTailoredResumeId() { return tailoredResumeId; }

    public void setTailoredResumeId(Long tailoredResumeId) { this.tailoredResumeId = tailoredResumeId; }

    public String getJobTitle() { return jobTitle; }

    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public ApplicationStatus getStatus() { return status; }

    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public OffsetDateTime getAppliedAt() { return appliedAt; }

    public void setAppliedAt(OffsetDateTime appliedAt) { this.appliedAt = appliedAt; }

    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}