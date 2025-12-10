package com.checkmate.checkmate_backend.modules.resume.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.user.domain.User;

import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(name = "tailored_resumes")
public class TailoredResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner of this tailored resume
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The job this resume was tailored for
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    // The master profile snapshot used
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "master_profile_id", nullable = false)
    private MasterProfile masterProfile;

    // Arbitrary structured JSON that frontend will render
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "resume_json", columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> resumeJson;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public TailoredResume() {
        // JPA
    }

    @PrePersist
    public void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
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

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public MasterProfile getMasterProfile() {
        return masterProfile;
    }

    public void setMasterProfile(MasterProfile masterProfile) {
        this.masterProfile = masterProfile;
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
