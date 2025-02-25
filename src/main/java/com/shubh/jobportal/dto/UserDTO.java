package com.shubh.jobportal.dto;


import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private AccountType accountType;
}
