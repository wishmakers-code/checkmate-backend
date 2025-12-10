package com.checkmate.checkmate_backend.modules.applications.service;

import java.util.List;

import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationResponse;
import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationStatusUpdateRequest;
import com.checkmate.checkmate_backend.modules.applications.dto.CreateApplicationRequest;

public interface ApplicationService {

    ApplicationResponse createApplication(Long userId, CreateApplicationRequest request);

    List<ApplicationResponse> getApplicationsForUser(Long userId);

    ApplicationResponse updateApplicationStatus(Long userId, Long applicationId, ApplicationStatusUpdateRequest request);

}