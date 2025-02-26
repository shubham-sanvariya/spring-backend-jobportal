package com.shubh.jobportal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.LoginDTO;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.entity.User;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.UserRepository;
import com.shubh.jobportal.utitlity.Utilities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new JobPortalException("USER_FOUND");
        }
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())) {
            throw new JobPortalException("INVALID_LOGIN_CREDENTIALS");
        }

        return user.toDTO();
    }
    
}
