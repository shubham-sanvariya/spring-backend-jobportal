package com.shubh.jobportal.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.LoginResponse;
import com.shubh.jobportal.security.jwt.JwtHelper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
    
    private final AuthenticationManager authManger;

    private final JwtHelper jwtHelper;

    public Authentication doAuthenticate(String usernameOrEmail, String password){
        var authToken = new UsernamePasswordAuthenticationToken(usernameOrEmail,password);
        try {
            Authentication auth = authManger.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return auth;
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
         catch (Exception e) {
           throw new RuntimeException("Failed to Authenticate", e);
        }
    }
}
