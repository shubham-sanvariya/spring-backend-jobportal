package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.UserDTO;


public interface UserService {

    UserDTO loginUser(LoginRequest loginDTO);

    UserDTO registerUser(UserDTO userDTO);

    UserDTO updateUserName(Long id, String updatedName);

    void sendOtp(String email) throws Exception;

    void verifyOTP(String email, String otp) throws Exception;

    void removeExpiredOTPs();

    void changePassword(LoginRequest resetDto);
}
