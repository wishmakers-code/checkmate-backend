package com.checkmate.checkmate_backend.modules.masterprofile.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(
    name = "projects",
    indexes = {
        @Index(name = "idx_projects_master_profile_id", columnList = "master_profile_id"),
        @Index(name = "idx_projects_experience_id", columnList = "experience_id")
    }
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "master_profile_id", nullable = false)
    private MasterProfile masterProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Type(JsonType.class)
    @Column(name = "tech_stack", columnDefinition = "jsonb")
    private List<TechItem> techStack;

    @Type(JsonType.class)
    @Column(name = "metrics", columnDefinition = "jsonb")
    private List<MetricEntry> metrics;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // ============================
    // Getters & Setters
    // ============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MasterProfile getMasterProfile() {
        return masterProfile;
    }

    public void setMasterProfile(MasterProfile masterProfile) {
        this.masterProfile = masterProfile;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
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
