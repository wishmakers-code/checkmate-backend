package com.checkmate.checkmate_backend.modules.masterprofile.service;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MetricEntry;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Project;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.TechItem;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.ExperienceRepository;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.MasterProfileRepository;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MasterProfileRepository masterProfileRepository;
    private final ExperienceRepository experienceRepository;

    public ProjectService(ProjectRepository projectRepository,
                          MasterProfileRepository masterProfileRepository,
                          ExperienceRepository experienceRepository) {
        this.projectRepository = projectRepository;
        this.masterProfileRepository = masterProfileRepository;
        this.experienceRepository = experienceRepository;
    }

    public List<Project> getProjectsForProfile(Long masterProfileId) {
        return projectRepository.findByMasterProfileId(masterProfileId);
    }

    public List<Project> getProjectsForExperience(Long experienceId) {
        return projectRepository.findByExperienceId(experienceId);
    }

    public Project getById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
    }

    @Transactional
    public Project createProject(Long masterProfileId,
                                 Long experienceId,
                                 String name,
                                 String summary,
                                 List<TechItem> techStack,
                                 List<MetricEntry> metrics) {
        MasterProfile profile = masterProfileRepository.findById(masterProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Master profile not found: " + masterProfileId));

        Experience experience = null;
        if (experienceId != null) {
            experience = experienceRepository.findById(experienceId)
                    .orElseThrow(() -> new IllegalArgumentException("Experience not found: " + experienceId));
        }

        Project project = new Project();
        project.setMasterProfile(profile);
        project.setExperience(experience);
        project.setName(name);
        project.setSummary(summary);
        project.setTechStack(techStack);
        project.setMetrics(metrics);

        OffsetDateTime now = OffsetDateTime.now();
        project.setCreatedAt(now);
        project.setUpdatedAt(now);

        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long projectId,
                                 String name,
                                 String summary,
                                 List<TechItem> techStack,
                                 List<MetricEntry> metrics,
                                 Long experienceId) {
        Project project = getById(projectId);

        if (name != null) {
            project.setName(name);
        }
        if (summary != null) {
            project.setSummary(summary);
        }
        if (techStack != null) {
            project.setTechStack(techStack);
        }
        if (metrics != null) {
            project.setMetrics(metrics);
        }
        if (experienceId != null) {
            Experience experience = experienceRepository.findById(experienceId)
                    .orElseThrow(() -> new IllegalArgumentException("Experience not found: " + experienceId));
            project.setExperience(experience);
        }

        project.setUpdatedAt(OffsetDateTime.now());
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}
