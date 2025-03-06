package com.shubh.jobportal.dto;

import java.time.LocalDateTime;

import com.shubh.jobportal.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
    private Long applicantId;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
}
