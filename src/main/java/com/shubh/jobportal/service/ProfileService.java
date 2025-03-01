package com.shubh.jobportal.service;

import com.shubh.jobportal.exception.JobPortalException;

public interface ProfileService {
    long createProfile(String email) throws JobPortalException;
}
