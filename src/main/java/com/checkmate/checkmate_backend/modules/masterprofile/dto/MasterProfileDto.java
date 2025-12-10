package com.checkmate.checkmate_backend.modules.masterprofile.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.ContactInfo;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;

public class MasterProfileDto {

    private Long id;
    private Long userId;
    private String headline;
    private String summary;
    private String location;
    private ContactInfo contactInfo;
    private List<SkillEntry> skills;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

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

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<SkillEntry> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillEntry> skills) {
        this.skills = skills;
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
