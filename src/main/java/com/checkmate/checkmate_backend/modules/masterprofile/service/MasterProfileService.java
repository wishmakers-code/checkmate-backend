package com.checkmate.checkmate_backend.modules.masterprofile.service;

import com.checkmate.checkmate_backend.modules.masterprofile.domain.ContactInfo;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;

import java.util.List;
import java.util.Optional;

public interface MasterProfileService {

    // Aggregate view (profile + experiences + projects)
    Optional<MasterProfileAggregate> getProfileAggregate(Long userId);

    // Simple lookup
    Optional<MasterProfile> findByUserId(Long userId);

    /**
     * Create or update a master profile for the given user.
     * - If a profile exists, update core fields.
     * - If none exists, create a new one.
     */
    MasterProfile createOrUpdateProfile(
            Long userId,
            String headline,
            String summary,
            String location,
            ContactInfo contactInfo,
            List<SkillEntry> skills
    );

    /**
     * Ensure a MasterProfile exists for the user.
     * - If it exists, return it.
     * - If none exists, create a minimal blank profile.
     * Used by the Resume Tailor flow so it never explodes on empty tables.
     */
    MasterProfile getOrCreateMasterProfileForUser(Long userId);
}
