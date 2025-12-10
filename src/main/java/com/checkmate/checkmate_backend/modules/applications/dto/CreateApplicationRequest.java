package com.checkmate.checkmate_backend.modules.applications.dto;

import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatus;

public class CreateApplicationRequest {

    private Long jobPostingId;
    private Long tailoredResumeId; // optional
    private String notes;
    private ApplicationStatus status; // optional; default to INTERESTED if null

    public Long getJobPostingId() { return jobPostingId; }

    public void setJobPostingId(Long jobPostingId) { this.jobPostingId = jobPostingId; }

    public Long getTailoredResumeId() { return tailoredResumeId; }

    public void setTailoredResumeId(Long tailoredResumeId) { this.tailoredResumeId = tailoredResumeId; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public ApplicationStatus getStatus() { return status; }

    public void setStatus(ApplicationStatus status) { this.status = status; }
}
