package com.checkmate.checkmate_backend.modules.masterprofile.service;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.Experience;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.ExperienceRepository;
import com.checkmate.checkmate_backend.modules.masterprofile.repository.MasterProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final MasterProfileRepository masterProfileRepository;

    public ExperienceService(ExperienceRepository experienceRepository,
                             MasterProfileRepository masterProfileRepository) {
        this.experienceRepository = experienceRepository;
        this.masterProfileRepository = masterProfileRepository;
    }

    public List<Experience> getExperiencesForProfile(Long masterProfileId) {
        return experienceRepository.findByMasterProfileIdOrderByStartDateDesc(masterProfileId);
    }

    public Experience getById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Experience not found: " + id));
    }

    @Transactional
    public Experience createExperience(Long masterProfileId,
                                       String companyName,
                                       String title,
                                       String location,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       boolean isCurrent,
                                       String description) {
        MasterProfile profile = masterProfileRepository.findById(masterProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Master profile not found: " + masterProfileId));

        Experience exp = new Experience();
        exp.setMasterProfile(profile);
        exp.setCompanyName(companyName);
        exp.setTitle(title);
        exp.setLocation(location);
        exp.setStartDate(startDate);
        exp.setEndDate(endDate);
        exp.setCurrent(isCurrent);
        exp.setDescription(description);

        OffsetDateTime now = OffsetDateTime.now();
        exp.setCreatedAt(now);
        exp.setUpdatedAt(now);

        return experienceRepository.save(exp);
    }

    @Transactional
    public Experience updateExperience(Long experienceId,
                                       String companyName,
                                       String title,
                                       String location,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Boolean isCurrent,
                                       String description) {
        Experience exp = getById(experienceId);

        if (companyName != null) {
            exp.setCompanyName(companyName);
        }
        if (title != null) {
            exp.setTitle(title);
        }
        if (location != null) {
            exp.setLocation(location);
        }
        if (startDate != null) {
            exp.setStartDate(startDate);
        }
        if (endDate != null) {
            exp.setEndDate(endDate);
        }
        if (isCurrent != null) {
            exp.setCurrent(isCurrent);
        }
        if (description != null) {
            exp.setDescription(description);
        }

        exp.setUpdatedAt(OffsetDateTime.now());
        return experienceRepository.save(exp);
    }

    @Transactional
    public void deleteExperience(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}
