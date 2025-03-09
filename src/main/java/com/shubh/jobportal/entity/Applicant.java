package com.shubh.jobportal.entity;

import java.time.LocalDateTime;
import java.util.Base64;

import com.shubh.jobportal.dto.ApplicantDTO;
import com.shubh.jobportal.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    private Long applicantId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private byte[] resume;
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;

    public ApplicantDTO toDto() {
        return new ApplicantDTO(
                this.applicantId, this.name, this.email, this.phone, this.website, this.resume != null ? Base64.getEncoder().encodeToString(this.resume) : null, this.coverLetter,
                this.timeStamp, this.applicationStatus,this.interviewTime);
    }

}
