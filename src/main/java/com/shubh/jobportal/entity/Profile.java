package com.shubh.jobportal.entity;

import java.util.Base64;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shubh.jobportal.dto.Certificate;
import com.shubh.jobportal.dto.Experience;
import com.shubh.jobportal.dto.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {
    @Id
    private Long id;
    private String name;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private Byte totalExperience;
    private byte[] picture;
    private List<String > skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;
    private List<Long> savedJobs;

    public ProfileDTO toDTO(){
        return new ProfileDTO(this.id, this.name,this.jobTitle,this.company,this.location,this.about, this.picture != null ? Base64.getEncoder().encodeToString(this.picture) : null,this.totalExperience,this.skills,this.experiences,this.certificates,this.savedJobs);
    }
}
