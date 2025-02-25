package com.shubh.jobportal.dto;

import com.shubh.jobportal.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    @NotBlank(message = "Name is null or blank")
    private String name;
    @NotBlank(message = "Email is null or blank")
    private String email;
    @NotBlank(message = "password is null or blank")
    private String password;

    private AccountType accountType;

    public User toEntity() {
        return new User(this.id, this.name, this.email, this.password, this.accountType);
    }
}
