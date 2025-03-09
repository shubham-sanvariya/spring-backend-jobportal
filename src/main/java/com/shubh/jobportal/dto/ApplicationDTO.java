package com.shubh.jobportal.dto;

import java.time.LocalDateTime;

import com.shubh.jobportal.enums.ApplicationStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private Long applicantId;

    private LocalDateTime interviewTime;

    @NotBlank
    private ApplicationStatus applicationStatus;
}
