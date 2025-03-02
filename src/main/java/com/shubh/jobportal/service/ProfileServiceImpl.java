package com.shubh.jobportal.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.ProfileDTO;
import com.shubh.jobportal.entity.Profile;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.ProfileRepository;
import com.shubh.jobportal.utitlity.Utilities;

import lombok.RequiredArgsConstructor;

@Service("profileService")
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Override
    public long createProfile(String email) throws JobPortalException {
        Profile profile = new Profile();
        profile.setId(Utilities.getNextSequence("profiles"));
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertificates(new ArrayList<>());
        profileRepository.save(profile);
        return profile.getId();
    }

    @Override
    public ProfileDTO getProfile(Long id) throws JobPortalException {
        return profileRepository.findById(id).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND")).toDTO();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
        profileRepository.findById(profileDTO.getId()).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));
        profileRepository.save(profileDTO.toEntity());
        return profileDTO;
    }
    
    
}
