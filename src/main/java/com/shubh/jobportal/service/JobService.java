package com.shubh.jobportal.service;

import java.util.List;

import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.exception.JobPortalException;

public interface JobService {
    JobDTO postJob(JobDTO jobDTO) throws JobPortalException;

    List<JobDTO> getAllJobs() throws JobPortalException;

    JobDTO getJobById(Long id) throws JobPortalException;
}
