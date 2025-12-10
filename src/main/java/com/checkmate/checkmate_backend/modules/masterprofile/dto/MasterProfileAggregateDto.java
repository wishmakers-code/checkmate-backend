package com.checkmate.checkmate_backend.modules.masterprofile.dto;

import java.util.List;

public class MasterProfileAggregateDto {

    private MasterProfileDto profile;
    private List<ExperienceDto> experiences;
    private List<ProjectDto> projects;

    public MasterProfileDto getProfile() {
        return profile;
    }

    public void setProfile(MasterProfileDto profile) {
        this.profile = profile;
    }

    public List<ExperienceDto> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperienceDto> experiences) {
        this.experiences = experiences;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }
}
