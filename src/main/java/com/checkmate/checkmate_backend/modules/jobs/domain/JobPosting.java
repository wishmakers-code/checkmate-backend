package com.checkmate.checkmate_backend.modules.jobs.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.checkmate.checkmate_backend.modules.user.domain.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner of this saved job
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional external ID from job board (e.g. LinkedIn job id)
    @Column(name = "external_id")
    private String externalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "location")
    private String location;

    @Column(name = "source")
    private String source; // e.g., "LinkedIn", "Simplify", "Manual"

    @Column(name = "job_url", length = 2048)
    private String jobUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_posting_json", columnDefinition = "jsonb")
    private Map<String, Object> rawPostingJson;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "normalized_tags", columnDefinition = "jsonb")
    private List<String> normalizedTags;

    @Column(name = "saved_at", nullable = false, updatable = false)
    private OffsetDateTime savedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status = JobStatus.INTERESTED;

    @Column(name = "notes")
    private String notes;

    public JobPosting() {
        // JPA
    }

    @PrePersist
    public void onCreate() {
        if (savedAt == null) {
            savedAt = OffsetDateTime.now();
        }
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public OffsetDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(OffsetDateTime savedAt) {
        this.savedAt = savedAt;
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
