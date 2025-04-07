package com.shubh.jobportal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.service.UserService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/changePass")
    public ResponseEntity<String> changePassword(@RequestBody @Valid LoginRequest resetDto)
         {
        userService.changePassword(resetDto);
        return new ResponseEntity<>("Password Changed Successfully.", HttpStatus.OK);
    }

    @PatchMapping("/update/{id}/username")
    public ResponseEntity<UserDTO> updateUserName(@PathVariable Long id, @RequestParam String userName){
        return new ResponseEntity<UserDTO>(userService.updateUserName(id, userName),HttpStatus.CREATED);
    }
}
