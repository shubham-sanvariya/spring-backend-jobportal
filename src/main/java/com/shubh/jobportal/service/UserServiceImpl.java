package com.shubh.jobportal.service;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.entity.User;
import com.shubh.jobportal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }
    
}
