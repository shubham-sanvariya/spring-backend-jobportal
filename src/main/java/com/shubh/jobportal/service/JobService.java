package com.shubh.jobportal.service;

import java.util.List;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.ApplicationDTO;
import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.entity.Job;

public interface JobService {
    JobDTO postJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    Job getJobById(Long id);

    JobDTO getJobDTOById(Long id);

    void applyJob(Long id, ApplicantDTO applicantDTO);

    void changeApplicationStatus(ApplicationDTO applicationDTO);

    List<JobDTO> getJobsByPostedBy(Long id);
}
