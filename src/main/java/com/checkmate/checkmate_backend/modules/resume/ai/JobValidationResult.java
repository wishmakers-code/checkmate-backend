package com.checkmate.checkmate_backend.modules.resume.ai;

import java.util.List;

public class JobValidationResult {

    private final boolean hasTitle;
    private final boolean hasCompany;
    private final boolean hasNormalizedTags;
    private final boolean hasRawPostingJson;
    private final List<String> warnings;

    public JobValidationResult(boolean hasTitle,
                               boolean hasCompany,
                               boolean hasNormalizedTags,
                               boolean hasRawPostingJson,
                               List<String> warnings) {
        this.hasTitle = hasTitle;
        this.hasCompany = hasCompany;
        this.hasNormalizedTags = hasNormalizedTags;
        this.hasRawPostingJson = hasRawPostingJson;
        this.warnings = warnings;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }

    public boolean isHasCompany() {
        return hasCompany;
    }

    public boolean isHasNormalizedTags() {
        return hasNormalizedTags;
    }

    public boolean isHasRawPostingJson() {
        return hasRawPostingJson;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
