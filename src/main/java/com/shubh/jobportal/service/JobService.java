package com.shubh.jobportal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.ApplicationDTO;
import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.entity.Job;
import com.shubh.jobportal.enums.JobStatus;

public interface JobService {
    JobDTO postJob(JobDTO jobDTO);

    Page<JobDTO> getAllJobs(Pageable pageable);

    Job getJobById(Long id);

    JobDTO getJobDTOById(Long id);

    void applyJob(Long id, ApplicantDTO applicantDTO);

    void changeApplicationStatus(ApplicationDTO applicationDTO);

    void updateJobStatus(Long jobId, String jobStatus);

    List<JobDTO> getJobsByPostedBy(Long id);

    Page<JobDTO> getJobsByJobStatus(JobStatus jobStatus, Pageable pageable);

    Page<JobDTO> getJobsByCompany(String company, Pageable pageable);
}
