package com.checkmate.checkmate_backend.modules.resume.ai;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Component
public class MockAiClient implements AiClient {

    @Override
    public Map<String, Object> generateTailoredResume(MasterProfile masterProfile, JobPosting jobPosting) {

        // 🔍 Analyze skills and job quality
        SkillAlignmentResult alignment = ResumeAnalysisUtil.analyzeSkillAlignment(masterProfile, jobPosting);
        JobValidationResult jobValidation = ResumeAnalysisUtil.analyzeJob(jobPosting);

        // Choose "primary" skills from matched skills if any, otherwise fall back to job tags
        List<String> primarySkills = !alignment.getMatchedSkills().isEmpty()
                ? alignment.getMatchedSkills()
                : (jobPosting.getNormalizedTags() != null ? jobPosting.getNormalizedTags() : List.of());

        Map<String, Object> header = Map.of(
                "fullName", masterProfile.getUser() != null ? masterProfile.getUser().getFullName() : "Candidate Name",
                "headline", safe(masterProfile.getHeadline(), "Senior Software Engineer"),
                "location", masterProfile.getLocation(),
                "contact", masterProfile.getContactInfo()
        );

        Map<String, Object> targetJob = Map.of(
                "title", safe(jobPosting.getTitle(), "Target Role"),
                "company", safe(jobPosting.getCompany(), "Target Company"),
                "location", jobPosting.getLocation(),
                "source", jobPosting.getSource(),
                "tags", jobPosting.getNormalizedTags() != null
                        ? jobPosting.getNormalizedTags()
                        : List.of()
        );

        Map<String, Object> skills = Map.of(
                "primary", primarySkills,
                "secondary", alignment.getExtraProfileSkills(),   // skills you have that job doesn’t list
                "tools", List.of("GitHub Actions", "Terraform", "PostgreSQL")
        );

        Map<String, Object> experienceEntry = Map.of(
                "company", "Current Company",
                "title", safe(masterProfile.getHeadline(), "Senior Software Engineer"),
                "location", masterProfile.getLocation(),
                "startDate", "2019-01",
                "endDate", "Present",
                "bullets", List.of(
                        "Designed and owned internal platforms used by multiple teams.",
                        "Automated deployment workflows to reduce lead time and incidents."
                ),
                "tags", List.of("Backend", "DevOps", "Platform")
        );

        Map<String, Object> projectEntry = Map.of(
                "name", "Checkmate",
                "role", "Builder",
                "bullets", List.of(
                        "Built AI-assisted resume tailoring engine for job postings.",
                        "Implemented Spring Boot backend and Next.js frontend."
                ),
                "tags", List.of("Side Project", "AI", "Career Tools")
        );

        Map<String, Object> analysis = Map.of(
                "matchedSkills", alignment.getMatchedSkills(),
                "missingSkills", alignment.getMissingSkills(),
                "extraProfileSkills", alignment.getExtraProfileSkills(),
                "jobValidation", Map.of(
                        "hasTitle", jobValidation.isHasTitle(),
                        "hasCompany", jobValidation.isHasCompany(),
                        "hasNormalizedTags", jobValidation.isHasNormalizedTags(),
                        "hasRawPostingJson", jobValidation.isHasRawPostingJson(),
                        "warnings", jobValidation.getWarnings()
                )
        );

        Map<String, Object> meta = Map.of(
                "aiModel", "mock",
                "aiSource", "mock-v2-analysis",
                "generatedAt", OffsetDateTime.now().toString(),
                "version", 1,
                "analysis", analysis   // ⭐️ New analysis block
        );

        return Map.of(
                "header", header,
                "summary", safe(
                        masterProfile.getSummary(),
                        "Experienced engineer with strong backend and platform background, tailored for this role."
                ),
                "targetJob", targetJob,
                "skills", skills,
                "experience", List.of(experienceEntry),
                "projects", List.of(projectEntry),
                "meta", meta
        );
    }

    private String safe(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
