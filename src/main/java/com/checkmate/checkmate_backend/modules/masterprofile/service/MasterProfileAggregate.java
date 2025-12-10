package com.checkmate.checkmate_backend.modules.masterprofile.service;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Project;

import java.util.List;

/**
 * A service-layer aggregate holding a MasterProfile together with its
 * Experiences and Projects.
 *
 * This is the internal representation returned by MasterProfileService
 * before the controller maps it into MasterProfileAggregateDto.
 */
public class MasterProfileAggregate {

    private final MasterProfile profile;
    private final List<Experience> experiences;
    private final List<Project> projects;

    public MasterProfileAggregate(MasterProfile profile,
                                  List<Experience> experiences,
                                  List<Project> projects) {
        this.profile = profile;
        this.experiences = experiences;
        this.projects = projects;
    }

    public MasterProfile getProfile() {
        return profile;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public List<Project> getProjects() {
        return projects;
    }
}
