package com.shubh.jobportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.ProfileDTO;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) throws JobPortalException{
        return new ResponseEntity<ProfileDTO>(profileService.getProfile(id),HttpStatus.OK);
    }
}
