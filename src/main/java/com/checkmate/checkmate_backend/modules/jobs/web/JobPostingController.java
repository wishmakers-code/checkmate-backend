package com.checkmate.checkmate_backend.modules.jobs.web;

import com.checkmate.checkmate_backend.modules.jobs.domain.JobStatus;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingResponse;
import com.checkmate.checkmate_backend.modules.jobs.dto.JobPostingSaveRequest;
import com.checkmate.checkmate_backend.modules.jobs.service.JobPostingService;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    /**
     * Get all saved jobs for a user.
     * Example: GET /api/v1/jobs/saved?userId=1
     */
    @GetMapping("/saved")
    public ResponseEntity<Page<JobPostingResponse>> getSavedJobs(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "status", required = false) JobStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<JobPostingResponse> result = jobPostingService.getSavedJobsForUser(
                userId, query, location, tag, status, pageable
        );
        return ResponseEntity.ok(result);
    }

    /**
     * Save a job for a user.
     * Example: POST /api/v1/jobs/save?userId=1
     */
    @PostMapping("/save")
    public ResponseEntity<JobPostingResponse> saveJob(
            @RequestParam("userId") Long userId,
            @RequestBody JobPostingSaveRequest request
    ) {
        JobPostingResponse saved = jobPostingService.saveJobForUser(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get a single job by id for a user.
     * Example: GET /api/v1/jobs/42?userId=1
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponse> getJobById(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId
    ) {
        JobPostingResponse job = jobPostingService.getJobForUser(userId, id);
        return ResponseEntity.ok(job);
    }
}