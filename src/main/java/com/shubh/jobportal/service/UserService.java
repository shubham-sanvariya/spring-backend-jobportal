package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.LoginDTO;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.exception.JobPortalException;


public interface UserService {

    UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException;

    UserDTO registerUser(UserDTO userDTO) throws JobPortalException;

    void sendOtp(String email) throws Exception;

    void verifyOTP(String email, String otp) throws Exception;
}
