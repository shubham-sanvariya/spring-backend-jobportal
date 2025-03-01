package com.shubh.jobportal.entity;

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
    private String email;
    private String jobTitle;
    private String company;
    private String loacation;
    private String about;

    private List<String > skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;

    public ProfileDTO toDTO(){
        return new ProfileDTO(this.id,this.email,this.jobTitle,this.company,this.loacation,this.about,this.skills,this.experiences,this.certificates);
    }
}
