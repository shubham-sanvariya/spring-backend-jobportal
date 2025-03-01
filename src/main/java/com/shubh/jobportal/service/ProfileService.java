package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.ProfileDTO;
import com.shubh.jobportal.exception.JobPortalException;

public interface ProfileService {
    long createProfile(String email) throws JobPortalException;

    ProfileDTO getProfile(Long id) throws JobPortalException;
}
