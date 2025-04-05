package com.shubh.jobportal.security.userDetails;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.entity.User;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> optionalUser = isEmail(usernameOrEmail) 
        ? userRepository.findByEmail(usernameOrEmail)
        : userRepository.findByName(usernameOrEmail);

        User user = optionalUser
                .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        return CustomUserDetails.buildUserDetails(user);
    }

    public boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        return input.matches(emailRegex);
    }
}
