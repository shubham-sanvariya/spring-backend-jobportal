package com.shubh.jobportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.ProfileDTO;
import com.shubh.jobportal.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
    
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id){
        return new ResponseEntity<ProfileDTO>(profileService.getProfile(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(){
        return new ResponseEntity<List<ProfileDTO>>(profileService.getAllProfileDTOs(),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO){
        return new ResponseEntity<ProfileDTO>(profileService.updateProfile(profileDTO),HttpStatus.OK);
    }

    @PatchMapping("/update/saved-jobs/{profileId}")
    public ResponseEntity<ProfileDTO> updateProfileSavedJobs(@PathVariable Long profileId, @RequestBody List<Long> jobIds){
        return new ResponseEntity<ProfileDTO>(profileService.updateProfileSavedJobs(profileId,jobIds), HttpStatus.OK);
    }
}
