package com.checkmate.checkmate_backend.modules.masterprofile.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.MetricEntry;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.TechItem;

public class ProjectDto {

    private Long id;
    private Long masterProfileId;
    private Long experienceId;
    private String name;
    private String summary;
    private List<TechItem> techStack;
    private List<MetricEntry> metrics;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMasterProfileId() {
        return masterProfileId;
    }

    public void setMasterProfileId(Long masterProfileId) {
        this.masterProfileId = masterProfileId;
    }

    public Long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<TechItem> getTechStack() {
        return techStack;
    }

    public void setTechStack(List<TechItem> techStack) {
        this.techStack = techStack;
    }

    public List<MetricEntry> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MetricEntry> metrics) {
        this.metrics = metrics;
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
