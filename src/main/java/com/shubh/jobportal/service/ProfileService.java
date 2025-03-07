package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.ProfileDTO;

public interface ProfileService {
    long createProfile(String email);

    ProfileDTO getProfile(Long id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);
}
