package com.checkmate.checkmate_backend.modules.resume.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public class TailoredResumeResponse {

    private Long id;
    private Long userId;
    private Long jobPostingId;
    private Long masterProfileId;

    private Map<String, Object> resumeJson;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(Long jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    public Long getMasterProfileId() {
        return masterProfileId;
    }

    public void setMasterProfileId(Long masterProfileId) {
        this.masterProfileId = masterProfileId;
    }

    public Map<String, Object> getResumeJson() {
        return resumeJson;
    }

    public void setResumeJson(Map<String, Object> resumeJson) {
        this.resumeJson = resumeJson;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
