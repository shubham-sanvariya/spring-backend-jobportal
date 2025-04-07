package com.shubh.jobportal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long userId;
    private String username;
    private String email;
    private Long profileId;
    private String token;
    private String authority;
}
