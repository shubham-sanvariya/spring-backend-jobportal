package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.LoginDTO;
import com.shubh.jobportal.dto.UserDTO;


public interface UserService {

    UserDTO loginUser(LoginDTO loginDTO);

    UserDTO registerUser(UserDTO userDTO);

    void sendOtp(String email) throws Exception;

    void verifyOTP(String email, String otp) throws Exception;

    void removeExpiredOTPs();

    void changePassword(LoginDTO resetDto);
}
