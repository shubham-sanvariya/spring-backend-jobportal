package com.shubh.jobportal.dto;

import java.util.Base64;
import java.util.List;

import com.shubh.jobportal.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String picture;
    private List<String > skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;
    private List<Long> savedJobs;

    public Profile toEntity() {
        return new Profile(this.id, this.jobTitle, this.company, this.location, this.about, this.picture != null ? Base64.getDecoder().decode(this.picture) : null, this.skills,
                this.experiences, this.certificates, this.savedJobs);
    }
}
