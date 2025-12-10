package com.checkmate.checkmate_backend.modules.applications.dto;

import com.checkmate.checkmate_backend.modules.applications.domain.ApplicationStatus;

public class ApplicationStatusUpdateRequest {

    private ApplicationStatus newStatus;
    private String comment;

    public ApplicationStatus getNewStatus() { return newStatus; }

    public void setNewStatus(ApplicationStatus newStatus) { this.newStatus = newStatus; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }
}
