package com.shubh.jobportal.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.shubh.jobportal.entity.Job;
import com.shubh.jobportal.enums.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long id;
    @Size(min = 4)
    private String jobTitle;
    @Size(min = 4)
    private String company;
    private List<ApplicantDTO> applicants;
    @Size(min = 30)
    private String about;
    @NotBlank
    private String experience;
    @NotBlank
    private String jobType;
    @Size(min = 4)
    private String location;
    @NotNull
    private Long packageOffered;
    private LocalDateTime postTime;
    @Size(min = 50)
    private String description;
    @NotEmpty
    private List<String> skillRequired;
    // @NotEmpty
    private JobStatus jobStatus;

    public Job toEntity() {
        return new Job(this.id, this.jobTitle, this.company, this.applicants != null ? this.applicants.stream().map(x -> x.toEntity()).toList() : null, this.about, this.experience, this.jobType,
                this.location, this.packageOffered, this.postTime, this.description, this.skillRequired,
                this.jobStatus);
    }
}
