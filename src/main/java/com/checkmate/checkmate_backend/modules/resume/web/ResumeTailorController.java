package com.checkmate.checkmate_backend.modules.resume.web;

import com.checkmate.checkmate_backend.modules.resume.dto.TailoredResumeResponse;
import com.checkmate.checkmate_backend.modules.resume.service.ResumeTailorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
public class ResumeTailorController {

    private final ResumeTailorService resumeTailorService;

    public ResumeTailorController(ResumeTailorService resumeTailorService) {
        this.resumeTailorService = resumeTailorService;
    }

    @PostMapping("/tailor/{jobPostingId}")
    public ResponseEntity<TailoredResumeResponse> tailorResume(
            @PathVariable("jobPostingId") Long jobPostingId,
            @RequestParam("userId") Long userId
    ) {
        TailoredResumeResponse response =
                resumeTailorService.tailorResumeForJob(userId, jobPostingId);
        return ResponseEntity.ok(response);
    }
}