package com.shubh.jobportal.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    private String title;
    private String issuer;
    private LocalDateTime startDate;
    private String certificateId;
}
