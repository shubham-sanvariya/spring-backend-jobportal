package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.exception.JobPortalException;

public interface JobService {
    public JobDTO postJob(JobDTO jobDTO) throws JobPortalException;
}
