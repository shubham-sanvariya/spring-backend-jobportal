package com.shubh.jobportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.exception.JobPortalException;
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
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO) throws JobPortalException{
        return new ResponseEntity<JobDTO>(jobService.postJob(jobDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() throws JobPortalException{
        return new ResponseEntity<List<JobDTO>>(jobService.getAllJobs(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) throws JobPortalException{
        return new ResponseEntity<JobDTO>(jobService.getJobById(id),HttpStatus.OK);
    }
}
