package com.checkmate.checkmate_backend.modules.resume.ai;

import java.util.List;

public class SkillAlignmentResult {

    private final List<String> matchedSkills;
    private final List<String> missingSkills;         // job wants, profile doesn't clearly show
    private final List<String> extraProfileSkills;    // profile has, job doesn’t explicitly ask for

    public SkillAlignmentResult(List<String> matchedSkills,
                                List<String> missingSkills,
                                List<String> extraProfileSkills) {
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.extraProfileSkills = extraProfileSkills;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public List<String> getExtraProfileSkills() {
        return extraProfileSkills;
    }
}
