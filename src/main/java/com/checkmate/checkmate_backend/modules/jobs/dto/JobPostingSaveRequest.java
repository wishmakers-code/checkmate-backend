package com.checkmate.checkmate_backend.modules.jobs.dto;

import java.util.List;
import java.util.Map;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobStatus;

public class JobPostingSaveRequest {

    private String externalId;
    private String title;
    private String company;
    private String location;
    private String source;
    private String jobUrl;

    private Map<String, Object> rawPostingJson;
    private List<String> normalizedTags;

    private JobStatus status;
    private String notes;

    // Getters & Setters

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public Map<String, Object> getRawPostingJson() {
        return rawPostingJson;
    }

    public void setRawPostingJson(Map<String, Object> rawPostingJson) {
        this.rawPostingJson = rawPostingJson;
    }

    public List<String> getNormalizedTags() {
        return normalizedTags;
    }

    public void setNormalizedTags(List<String> normalizedTags) {
        this.normalizedTags = normalizedTags;
    }

        public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
