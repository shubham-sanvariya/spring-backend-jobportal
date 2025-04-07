package com.shubh.jobportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.security.jwt.JwtHelper;
import com.shubh.jobportal.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    
    private final JwtHelper jwtHelper;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String > login(@RequestBody @Valid LoginRequest req){
        return new ResponseEntity<String >(authService.login(req),HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) throws MessagingException {
            authService.registerUser(userDTO);
            return new ResponseEntity<>("User Registered Succesfully", HttpStatus.CREATED);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOTP(@RequestParam @Email(message = "{user.email.invalid}") String email, @RequestParam String check) throws MessagingException{
        authService.sendOtp(email, check);
        return new ResponseEntity<>("OTP sent successfully.",HttpStatus.OK);
    }

    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<String> verifyOTP(@PathVariable @Email(message = "{user.email.invalid}") String email,@PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}") String otp) throws Exception{
        authService.verifyOTP(email,otp);
        return new ResponseEntity<>("OTP has been verified.",HttpStatus.OK);
    }
}
