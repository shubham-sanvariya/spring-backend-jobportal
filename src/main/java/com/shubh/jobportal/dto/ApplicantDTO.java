package com.shubh.jobportal.dto;

import java.time.LocalDateTime;
import java.util.Base64;

import com.shubh.jobportal.entity.Applicant;
import com.shubh.jobportal.enums.ApplicationStatus;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantDTO {
    @NotNull
    private Long applicantId;
     @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Phone number must be exactly 10 digits")
    private Long phone;

    @Size(max = 255, message = "Website URL is too long")
    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$", message = "Invalid website URL")
    private String website;

    @NotBlank(message = "Resume is required")
    private String resume; 
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;

    public Applicant toEntity() {
        return new Applicant(
            this.applicantId, this.name, this.email, this.phone, this.website, this.resume != null ? Base64.getDecoder().decode(this.resume) : null, this.coverLetter,
                this.timeStamp, this.applicationStatus,this.interviewTime
        );
    }
}
