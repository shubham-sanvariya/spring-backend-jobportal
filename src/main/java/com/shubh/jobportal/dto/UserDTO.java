package com.shubh.jobportal.dto;

import com.shubh.jobportal.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "{user.name.absent}")
    @Size(min = 3,max = 60)
    private String name;

    @NotBlank(message = "{user.email.absent}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.absent}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$", message = "{user.password.invalid}")
    private String password;

    @NotBlank
    private AccountType accountType;
    
    private Long profileId;

    public User toEntity() {
        return new User(this.id, this.name, this.email, this.password, this.accountType,this.profileId);
    }
}
