package com.checkmate.checkmate_backend.modules.applications.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationResponse;
import com.checkmate.checkmate_backend.modules.applications.dto.ApplicationStatusUpdateRequest;
import com.checkmate.checkmate_backend.modules.applications.dto.CreateApplicationRequest;
import com.checkmate.checkmate_backend.modules.applications.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Create a new application for a job (optionally linked to a tailored resume).
     *
     * POST /api/v1/applications?userId=1
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @RequestParam("userId") Long userId,
            @RequestBody CreateApplicationRequest request
    ) {
        ApplicationResponse response = applicationService.createApplication(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * List all applications for a user.
     *
     * GET /api/v1/applications?userId=1
     */
    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getApplications(
            @RequestParam("userId") Long userId
    ) {
        List<ApplicationResponse> responses = applicationService.getApplicationsForUser(userId);
        return ResponseEntity.ok(responses);
    }

    /**
     * Update the status of an application and append to status history.
     *
     * PUT /api/v1/applications/{id}/status?userId=1
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable("id") Long applicationId,
            @RequestParam("userId") Long userId,
            @RequestBody ApplicationStatusUpdateRequest request
    ) {
        ApplicationResponse response =
                applicationService.updateApplicationStatus(userId, applicationId, request);
        return ResponseEntity.ok(response);
    }
}