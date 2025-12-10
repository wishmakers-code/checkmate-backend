package com.checkmate.checkmate_backend.modules.masterprofile.service;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.ContactInfo;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.Project;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.MasterProfileRepository;
import com.checkmate.checkmate_backend.modules.user.domain.User;
import com.checkmate.checkmate_backend.modules.user.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MasterProfileServiceImpl implements MasterProfileService {

    private final MasterProfileRepository masterProfileRepository;
    private final UserRepository userRepository;
    private final ExperienceService experienceService;
    private final ProjectService projectService;

    public MasterProfileServiceImpl(MasterProfileRepository masterProfileRepository,
                                    UserRepository userRepository,
                                    ExperienceService experienceService,
                                    ProjectService projectService) {
        this.masterProfileRepository = masterProfileRepository;
        this.userRepository = userRepository;
        this.experienceService = experienceService;
        this.projectService = projectService;
    }

    // ============================
    // Interface methods
    // ============================

    @Override
    @Transactional(readOnly = true)
    public Optional<MasterProfileAggregate> getProfileAggregate(Long userId) {
        return masterProfileRepository.findByUserId(userId)
                .map(profile -> {
                    List<Experience> experiences =
                            experienceService.getExperiencesForProfile(profile.getId());
                    List<Project> projects =
                            projectService.getProjectsForProfile(profile.getId());
                    return new MasterProfileAggregate(profile, experiences, projects);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MasterProfile> findByUserId(Long userId) {
        return masterProfileRepository.findByUserId(userId);
    }

    @Override
    public MasterProfile createOrUpdateProfile(
            Long userId,
            String headline,
            String summary,
            String location,
            ContactInfo contactInfo,
            List<SkillEntry> skills
    ) {
        MasterProfile profile = masterProfileRepository.findByUserId(userId)
                .orElseGet(() -> createNewProfileForUser(userId));

        profile.setHeadline(headline);
        profile.setSummary(summary);
        profile.setLocation(location);
        profile.setContactInfo(contactInfo);
        profile.setSkills(skills != null ? skills : Collections.emptyList());

        return masterProfileRepository.save(profile);
    }

    @Override
    public MasterProfile getOrCreateMasterProfileForUser(Long userId) {
        return masterProfileRepository.findByUserId(userId)
                .orElseGet(() -> createNewProfileForUser(userId));
    }

    // ============================
    // Helpers
    // ============================

    private MasterProfile createNewProfileForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        MasterProfile profile = new MasterProfile();
        profile.setUser(user);
        profile.setHeadline("Your headline here");
        profile.setSummary("Fill in your summary.");
        profile.setLocation(null);

        ContactInfo contact = new ContactInfo();
        contact.setEmail(user.getEmail());
        contact.setPhone(null);
        contact.setLinks(null);
        profile.setContactInfo(contact);

        profile.setSkills(Collections.emptyList());

        return masterProfileRepository.save(profile);
    }
}
