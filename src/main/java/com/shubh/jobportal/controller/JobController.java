package com.shubh.jobportal.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.ApplicationDTO;
import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.dto.PaginatedResponse;
import com.shubh.jobportal.enums.JobStatus;
import com.shubh.jobportal.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {
    
    private final JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO){
        return new ResponseEntity<JobDTO>(jobService.postJob(jobDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<JobDTO>> getAllJobs(@RequestParam(required = false) JobStatus jobStatus, Pageable pageable){
        Page<JobDTO> jobs = (jobStatus == null) ? jobService.getAllJobs(pageable) : jobService.getJobsByJobStatus(jobStatus,pageable);
        return new ResponseEntity<PaginatedResponse<JobDTO>>(PaginatedResponse.fromPage(jobs),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        return new ResponseEntity<JobDTO>(jobService.getJobDTOById(id),HttpStatus.OK);
    }

    @GetMapping("/posted-by/{id}")
    public ResponseEntity<List<JobDTO>> getJobsByPostedBy(@PathVariable Long id){
        return new ResponseEntity<List<JobDTO>>(jobService.getJobsByPostedBy(id),HttpStatus.OK);
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<String> applyJob(@PathVariable Long id, @RequestBody @Valid ApplicantDTO applicantDTO){
        jobService.applyJob(id,applicantDTO);
        return new ResponseEntity<String>("Applied Successfully",HttpStatus.OK);
    }

    @PutMapping("/applicant/update")
    public ResponseEntity<String> updateJobApplicant(@RequestBody @Valid ApplicationDTO applicationDTO){
        jobService.changeApplicationStatus(applicationDTO);
        return new ResponseEntity<String>("Job Application Status Updated",HttpStatus.OK);
    }

    @PatchMapping("/update/{id}/job-status")
    public ResponseEntity<String > updateJobStatus(@PathVariable Long id, @RequestParam String jobStatus){
        jobService.updateJobStatus(id,jobStatus);
        return new ResponseEntity<String>("Job Status Updated", HttpStatus.OK);
    }
}
