package com.shubh.jobportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.dto.ApplicationDTO;
import com.shubh.jobportal.dto.JobDTO;
import com.shubh.jobportal.dto.NotificationDTO;
import com.shubh.jobportal.entity.Applicant;
import com.shubh.jobportal.entity.Job;
import com.shubh.jobportal.enums.ApplicationStatus;
import com.shubh.jobportal.enums.JobStatus;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.JobRepository;
import com.shubh.jobportal.utitlity.Utilities;

import lombok.RequiredArgsConstructor;

@Service("jobService")
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final NotificationService notificationService;

    @Override
    public JobDTO postJob(JobDTO jobDTO) {
        if (jobDTO.getId() == 0) {
            jobDTO.setId(Utilities.getNextSequence("jobs"));
        } 
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public Page<JobDTO> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable).map(Job::toDTO);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
    }

    public JobDTO getJobDTOById(Long id) {
        Job job = getJobById(id);
        return job.toDTO();
    }

    @Override
    public void applyJob(Long id, ApplicantDTO applicantDTO) {
        Job job = getJobById(id);

        List<Applicant> applicants = job.getApplicants();
        if (applicants == null) {
            applicants = new ArrayList<>();
        }
        boolean alreadyApplied = applicants.stream()
                .anyMatch(app -> app.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new JobPortalException("JOB_APPLIED_ALREADY");
        }

        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());

        job.setApplicants(applicants);
        jobRepository.save(job);
    }

    @Override
    public void changeApplicationStatus(ApplicationDTO applicationDTO) {
        Job job = getJobById(applicationDTO.getId());

        List<Applicant> applicants = job.getApplicants().stream().map(applicant -> {
            if (applicant.getApplicantId() == applicationDTO.getApplicantId()) {
                applicant.setApplicationStatus(applicationDTO.getApplicationStatus());
                if (applicant.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)) {
                    applicant.setInterviewTime(applicationDTO.getInterviewTime());

                    NotificationDTO notificationDTO = new NotificationDTO();

                    notificationDTO.setUserId(applicant.getApplicantId());
                    notificationDTO.setAction("Interview Scheduled");
                    notificationDTO.setMessage("Interview scheduled for job id: " + applicant.getApplicantId());
                    notificationDTO.setTimeStamp(LocalDateTime.now());
                    notificationDTO.setRoute("/job-history");
                    
                    notificationService.sendNotification(notificationDTO);
                }
            }

            return applicant;
        }).collect(Collectors.toList());

        job.setApplicants(applicants);

        jobRepository.save(job);
    }

    @Override
    public List<JobDTO> getJobsByPostedBy(Long id) {
        return jobRepository.findByPostedBy(id).stream().map(job -> job.toDTO()).collect(Collectors.toList());
    }

    @Override
    public void updateJobStatus(Long jobId, String jobStatus) {
        Job job = getJobById(jobId);

        if (jobStatus.equalsIgnoreCase(JobStatus.CLOSED.toString())) {
            job.setJobStatus(JobStatus.CLOSED);
        } else if (jobStatus.equalsIgnoreCase(JobStatus.DRAFT.toString())) {
            job.setJobStatus(JobStatus.DRAFT);
        } else {
            job.setPostTime(LocalDateTime.now());
            job.setJobStatus(JobStatus.ACTIVE);
        }

        jobRepository.save(job);

    }

    @Override
    public Page<JobDTO> getJobsByJobStatus(JobStatus jobStatus, Pageable pageable) {
        if (jobStatus == null) {
            throw new JobPortalException("job status is null");
        }
        return jobRepository.findByJobStatus(jobStatus,pageable).map(Job::toDTO);
    }

    @Override
    public Page<JobDTO> getJobsByCompany(String companyName, Pageable pageable) {
        if (companyName.isEmpty()) {
            throw new JobPortalException("company name is empty");
        }

        return jobRepository.findByCompany(companyName,pageable).map(Job::toDTO);
    }

    
}
