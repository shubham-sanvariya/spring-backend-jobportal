package com.shubh.jobportal.service;

import java.util.List;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.JobDTO;

public interface JobService {
    JobDTO postJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    JobDTO getJobById(Long id);

    void applyJob(Long id, ApplicantDTO applicantDTO);
}
