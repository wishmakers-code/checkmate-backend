package com.checkmate.checkmate_backend.modules.masterprofile.web;

import com.checkmate.checkmate_backend.modules.masterprofile.dto.ExperienceDto;
import com.checkmate.checkmate_backend.modules.masterprofile.dto.MasterProfileAggregateDto;
import com.checkmate.checkmate_backend.modules.masterprofile.dto.MasterProfileDto;
import com.checkmate.checkmate_backend.modules.masterprofile.dto.ProjectDto;
import com.checkmate.checkmate_backend.modules.masterprofile.dto.UpdateMasterProfileRequest;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Project;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;
import com.checkmate.checkmate_backend.modules.masterprofile.service.MasterProfileService;
import com.checkmate.checkmate_backend.modules.masterprofile.service.ProjectService;
import com.checkmate.checkmate_backend.modules.masterprofile.service.ExperienceService;
import com.checkmate.checkmate_backend.modules.masterprofile.service.MasterProfileAggregate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-profile")
public class MasterProfileController {

    private final MasterProfileService masterProfileService;
    private final ExperienceService experienceService;
    private final ProjectService projectService;

    public MasterProfileController(MasterProfileService masterProfileService,
                                   ExperienceService experienceService,
                                   ProjectService projectService) {
        this.masterProfileService = masterProfileService;
        this.experienceService = experienceService;
        this.projectService = projectService;
    }

    /**
     * Get full aggregate (profile + experiences + projects) for a user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<MasterProfileAggregateDto> getMasterProfile(@PathVariable Long userId) {
        return masterProfileService.getProfileAggregate(userId)
                .map(aggregate -> ResponseEntity.ok(toAggregateDto(aggregate)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create or update the core master profile for a user.
     * POST is used here as an "upsert" for simplicity:
     * - If no profile exists, create it (201 Created).
     * - If a profile exists, update it (200 OK).
     */
    @PostMapping("/{userId}")
    public ResponseEntity<MasterProfileDto> createOrUpdateMasterProfile(
            @PathVariable Long userId,
            @RequestBody UpdateMasterProfileRequest request
    ) {
        List<SkillEntry> skills = request.getSkills();

        boolean exists = masterProfileService.findByUserId(userId).isPresent();

        MasterProfile saved = masterProfileService.createOrUpdateProfile(
                userId,
                request.getHeadline(),
                request.getSummary(),
                request.getLocation(),
                request.getContactInfo(),
                skills
        );

        MasterProfileDto dto = toProfileDto(saved);
        if (exists) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }
    }

    /**
     * Optional: if you want PUT to strictly require existing profile,
     * you can keep this, or just rely on POST upsert.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<MasterProfileDto> updateMasterProfile(
            @PathVariable Long userId,
            @RequestBody UpdateMasterProfileRequest request
    ) {
        return masterProfileService.findByUserId(userId)
                .map(existing -> {
                    List<SkillEntry> skills = request.getSkills();
                    MasterProfile updated = masterProfileService.createOrUpdateProfile(
                            userId,
                            request.getHeadline(),
                            request.getSummary(),
                            request.getLocation(),
                            request.getContactInfo(),
                            skills
                    );
                    return ResponseEntity.ok(toProfileDto(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/experiences")
    public ResponseEntity<List<ExperienceDto>> getExperiences(@PathVariable Long userId) {
        MasterProfile profile = masterProfileService.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));

        List<Experience> experiences = experienceService.getExperiencesForProfile(profile.getId());
        List<ExperienceDto> dtos = experiences.stream()
                .map(this::toExperienceDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<List<ProjectDto>> getProjects(@PathVariable Long userId) {
        MasterProfile profile = masterProfileService.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + userId));

        List<Project> projects = projectService.getProjectsForProfile(profile.getId());
        List<ProjectDto> dtos = projects.stream()
                .map(this::toProjectDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ============================
    // Mapping helpers
    // ============================

    private MasterProfileAggregateDto toAggregateDto(MasterProfileAggregate aggregate) {
        MasterProfileAggregateDto dto = new MasterProfileAggregateDto();
        dto.setProfile(toProfileDto(aggregate.getProfile()));

        List<ExperienceDto> experienceDtos = aggregate.getExperiences().stream()
                .map(this::toExperienceDto)
                .collect(Collectors.toList());
        dto.setExperiences(experienceDtos);

        List<ProjectDto> projectDtos = aggregate.getProjects().stream()
                .map(this::toProjectDto)
                .collect(Collectors.toList());
        dto.setProjects(projectDtos);

        return dto;
    }

    private MasterProfileDto toProfileDto(MasterProfile profile) {
        MasterProfileDto dto = new MasterProfileDto();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setHeadline(profile.getHeadline());
        dto.setSummary(profile.getSummary());
        dto.setLocation(profile.getLocation());
        dto.setContactInfo(profile.getContactInfo());
        dto.setSkills(profile.getSkills());
        dto.setCreatedAt(profile.getCreatedAt());
        dto.setUpdatedAt(profile.getUpdatedAt());
        return dto;
    }

    private ExperienceDto toExperienceDto(Experience exp) {
        ExperienceDto dto = new ExperienceDto();
        dto.setId(exp.getId());
        dto.setMasterProfileId(exp.getMasterProfile().getId());
        dto.setCompanyName(exp.getCompanyName());
        dto.setTitle(exp.getTitle());
        dto.setLocation(exp.getLocation());
        dto.setStartDate(exp.getStartDate());
        dto.setEndDate(exp.getEndDate());
        dto.setCurrent(exp.isCurrent());
        dto.setDescription(exp.getDescription());
        dto.setCreatedAt(exp.getCreatedAt());
        dto.setUpdatedAt(exp.getUpdatedAt());
        return dto;
    }

    private ProjectDto toProjectDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setMasterProfileId(project.getMasterProfile().getId());
        dto.setExperienceId(project.getExperience() != null ? project.getExperience().getId() : null);
        dto.setName(project.getName());
        dto.setSummary(project.getSummary());
        dto.setTechStack(project.getTechStack());
        dto.setMetrics(project.getMetrics());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        return dto;
    }
}
