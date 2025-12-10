package com.checkmate.checkmate_backend.modules.masterprofile.dto;

import java.util.List;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.ContactInfo;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;

public class UpdateMasterProfileRequest {

    private String headline;
    private String summary;
    private String location;
    private ContactInfo contactInfo;
    private List<SkillEntry> skills;

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
}
