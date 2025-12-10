package com.checkmate.checkmate_backend.modules.masterprofile.repository;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByMasterProfile(MasterProfile masterProfile);

    List<Project> findByMasterProfileId(Long masterProfileId);

    List<Project> findByExperience(Experience experience);

    List<Project> findByExperienceId(Long experienceId);
}
