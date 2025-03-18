package com.shubh.jobportal.service;

import java.util.List;

import com.shubh.jobportal.dto.ProfileDTO;

public interface ProfileService {
    long createProfile(String email);

    ProfileDTO getProfile(Long id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);

    ProfileDTO updateProfileSavedJobs(Long profileId, List<Long> jobIds);

    List<ProfileDTO> getAllProfileDTOs();
}
