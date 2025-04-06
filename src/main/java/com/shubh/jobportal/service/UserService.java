package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.UserDTO;


public interface UserService {

    UserDTO updateUserName(Long id, String updatedName);

    void removeExpiredOTPs();

    void changePassword(LoginRequest resetDto);
}
