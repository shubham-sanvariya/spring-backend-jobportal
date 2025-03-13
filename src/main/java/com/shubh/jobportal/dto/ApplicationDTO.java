package com.shubh.jobportal.dto;

import java.time.LocalDateTime;

import com.shubh.jobportal.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    @NotNull
    private Long id;
    @NotNull
    private Long applicantId;

    private LocalDateTime interviewTime;

    @NotNull
    private ApplicationStatus applicationStatus;
}
