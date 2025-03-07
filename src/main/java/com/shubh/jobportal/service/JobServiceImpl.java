package com.shubh.jobportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.entity.Applicant;
import com.shubh.jobportal.entity.Job;
import com.shubh.jobportal.enums.ApplicationStatus;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.JobRepository;
import com.shubh.jobportal.utitlity.Utilities;

import lombok.RequiredArgsConstructor;

@Service("jobService")
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
     private final JobRepository jobRepository;

    @Override
    public JobDTO postJob(JobDTO jobDTO) {
        jobDTO.setId(Utilities.getNextSequence("jobs"));
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream().map(job -> job.toDTO()).collect(Collectors.toList());
    }

    @Override
    public JobDTO getJobById(Long id) {
       return jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")).toDTO();
    }

    @Override
    public void applyJob(Long id, ApplicantDTO applicantDTO) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));

        List<Applicant> applicants = job.getApplicants();
        if (applicants == null) {
            applicants = new ArrayList<>();
        }
        boolean alreadyApplied = applicants.stream().anyMatch(app -> app.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new JobPortalException("JOB_APPLIED_ALREADY");
        }

        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());

        job.setApplicants(applicants);
        jobRepository.save(job);
    }

     
}
