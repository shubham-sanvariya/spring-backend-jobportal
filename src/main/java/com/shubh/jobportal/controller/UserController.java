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
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.LoginDTO;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO){
        return new ResponseEntity<>(userService.registerUser(userDTO),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO){
        return new ResponseEntity<>(userService.loginUser(loginDTO),HttpStatus.OK);
    }

    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<String> sendOTP(@PathVariable @Email(message = "{user.email.invalid}") String email) throws Exception{
        userService.sendOtp(email);
        return new ResponseEntity<>("OTP sent successfully.",HttpStatus.OK);
    }

    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<String> verifyOTP(@PathVariable @Email(message = "{user.email.invalid}") String email,@PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}") String otp) throws Exception{
        userService.verifyOTP(email,otp);
        return new ResponseEntity<>("OTP has been verified.",HttpStatus.OK);
    }


    @PostMapping("/changePass")
    public ResponseEntity<String> changePassword(@RequestBody @Valid LoginDTO resetDto)
         {
        userService.changePassword(resetDto);
        return new ResponseEntity<>("Password Changed Successfully.", HttpStatus.OK);
    }
}
