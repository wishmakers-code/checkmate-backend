package com.checkmate.checkmate_backend.modules.resume.ai;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.SkillEntry;

import java.util.*;
import java.util.stream.Collectors;

public class ResumeAnalysisUtil {

    /**
     * Compare MasterProfile skills with JobPosting tags.
     *
     * Assumes SkillEntry has a getName() method. If your field is named differently
     * (e.g. getLabel()), just update the mapping below.
     */
    public static SkillAlignmentResult analyzeSkillAlignment(
            MasterProfile masterProfile,
            JobPosting jobPosting
    ) {
        List<SkillEntry> skillEntries =
                masterProfile.getSkills() != null ? masterProfile.getSkills() : List.of();

        // Extract profile skill names
        Set<String> profileSkills = skillEntries.stream()
                .map(SkillEntry::getName)          // 🔁 change here if your field is different
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Extract job tags
        List<String> tags = jobPosting.getNormalizedTags() != null
                ? jobPosting.getNormalizedTags()
                : List.of();

        Set<String> jobSkills = tags.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Compute sets
        Set<String> matched = new LinkedHashSet<>(profileSkills);
        matched.retainAll(jobSkills);

        Set<String> missing = new LinkedHashSet<>(jobSkills);
        missing.removeAll(profileSkills);

        Set<String> extra = new LinkedHashSet<>(profileSkills);
        extra.removeAll(jobSkills);

        return new SkillAlignmentResult(
                matched.stream().toList(),
                missing.stream().toList(),
                extra.stream().toList()
        );
    }

    /**
     * Basic validation on the job posting so we know how "good" our input is.
     */
    public static JobValidationResult analyzeJob(JobPosting jobPosting) {
        boolean hasTitle = jobPosting.getTitle() != null && !jobPosting.getTitle().isBlank();
        boolean hasCompany = jobPosting.getCompany() != null && !jobPosting.getCompany().isBlank();
        boolean hasTags = jobPosting.getNormalizedTags() != null && !jobPosting.getNormalizedTags().isEmpty();
        boolean hasRawJson = jobPosting.getRawPostingJson() != null && !jobPosting.getRawPostingJson().isEmpty();

        List<String> warnings = new ArrayList<>();

        if (!hasTitle) {
            warnings.add("Job posting is missing a title.");
        }
        if (!hasCompany) {
            warnings.add("Job posting is missing a company.");
        }
        if (!hasTags) {
            warnings.add("Job posting has no normalized tags; tailoring may be less accurate.");
        }
        if (!hasRawJson) {
            warnings.add("Job posting has no raw JSON payload; description text may be unavailable.");
        }

        return new JobValidationResult(
                hasTitle,
                hasCompany,
                hasTags,
                hasRawJson,
                warnings
        );
    }
}
