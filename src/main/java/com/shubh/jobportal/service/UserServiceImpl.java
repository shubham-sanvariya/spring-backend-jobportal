package com.shubh.jobportal.service;

import org.springframework.stereotype.Service;

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

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        userDTO.setId(Utilities.getNextSequence("users"));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }
    
}
