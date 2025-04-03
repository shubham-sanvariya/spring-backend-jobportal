package com.shubh.jobportal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shubh.jobportal.dto.ProfileDTO;

public interface ProfileService {
    long createProfile(String email);

    ProfileDTO getProfile(Long id);

    ProfileDTO updateProfile(ProfileDTO profileDTO);

    ProfileDTO updateProfileSavedJobs(Long profileId, List<Long> jobIds);

    Page<ProfileDTO> getAllProfileDTOs(Pageable pageable);
}
