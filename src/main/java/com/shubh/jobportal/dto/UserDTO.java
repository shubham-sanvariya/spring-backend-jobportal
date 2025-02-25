package com.shubh.jobportal.dto;

import com.shubh.jobportal.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private AccountType accountType;

    public User toEntity() {
        return new User(this.id, this.name, this.email, this.password, this.accountType);
    }
}
