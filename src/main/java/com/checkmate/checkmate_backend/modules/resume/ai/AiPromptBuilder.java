package com.checkmate.checkmate_backend.modules.resume.ai;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobPosting;
import com.checkmate.checkmate_backend.modules.masterprofile.domain.MasterProfile;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AiPromptBuilder {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String buildTailoredResumePrompt(
            MasterProfile masterProfile,
            JobPosting jobPosting
    ) {
        try {
            String profileJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(masterProfile);

            String jobJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jobPosting);

            return """
                    You are an expert technical resume writer with 20+ years of experience
                    specializing in ATS-optimized resumes for software engineers.

                    -----------------------------
                    🎯 TASK
                    -----------------------------
                    Create a tailored resume JSON object that:
                    - Emphasizes alignment with the target job
                    - Reflects the strongest technical and impact-focused elements
                    - Incorporates job description keywords naturally
                    - Uses short, punchy bullet points beginning with powerful verbs
                    - Avoids filler, generic statements, and fluff

                    -----------------------------
                    📦 OUTPUT FORMAT (STRICT)
                    -----------------------------
                    Return ONLY a JSON object matching this schema:

                    {
                      "header": {
                        "fullName": "string",
                        "headline": "string",
                        "location": "string",
                        "contact": {
                          "email": "string",
                          "phone": "string | null",
                          "links": ["string"]
                        }
                      },
                      "summary": "string",
                      "targetJob": {
                        "title": "string",
                        "company": "string",
                        "location": "string | null",
                        "source": "string | null",
                        "tags": ["string"]
                      },
                      "skills": {
                        "primary": ["string"],
                        "secondary": ["string"],
                        "tools": ["string"]
                      },
                      "experience": [
                        {
                          "company": "string",
                          "title": "string",
                          "location": "string | null",
                          "startDate": "YYYY-MM or null",
                          "endDate": "YYYY-MM or null",
                          "bullets": ["string"],
                          "tags": ["string"]
                        }
                      ],
                      "projects": [
                        {
                          "name": "string",
                          "role": "string",
                          "bullets": ["string"],
                          "tags": ["string"]
                        }
                      ],
                      "meta": {
                        "aiModel": "string",
                        "aiSource": "string",
                        "generatedAt": "ISO8601",
                        "version": 1
                      }
                    }

                    -----------------------------
                    🧩 MASTER PROFILE (SOURCE DATA)
                    -----------------------------
                    %s

                    -----------------------------
                    📄 JOB POSTING (SOURCE DATA)
                    -----------------------------
                    %s

                    -----------------------------
                    🚦 RULES
                    -----------------------------
                    - Use strong action verbs
                    - Prioritize accomplishments over responsibilities
                    - Quantify impact when possible
                    - Reflect job keywords without overstuffing
                    - Tailor bullets to match the target role expectations
                    - Ensure output is VALID JSON with NO commentary or markdown

                    -----------------------------
                    🔥 NOW GENERATE THE JSON RESUME BELOW
                    -----------------------------
                    """.formatted(profileJson, jobJson);

        } catch (Exception e) {
            throw new RuntimeException("Error building prompt", e);
        }
    }
}
