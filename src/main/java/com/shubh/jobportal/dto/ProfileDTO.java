package com.shubh.jobportal.dto;

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

    private List<String > skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;

    public Profile toEntity() {
        return new Profile(this.id, this.jobTitle, this.company, this.location, this.about, this.skills,
                this.experiences, this.certificates);
    }
}
