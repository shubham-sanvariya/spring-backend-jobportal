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
    private String name;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String picture;
    private Byte totalExperience;
    private List<String > skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;
    private List<Long> savedJobs;

    public Profile toEntity() {
        byte[] pictureBytes = this.picture != null ? Base64.getDecoder().decode(this.picture) : null;
        return new Profile(this.id, this.name, this.jobTitle, this.company, this.location, this.about,
                this.totalExperience, pictureBytes, this.skills,
                this.experiences, this.certificates, this.savedJobs);
    }
}
