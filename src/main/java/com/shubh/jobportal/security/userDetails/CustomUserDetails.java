package com.shubh.jobportal.security.userDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shubh.jobportal.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{
    
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long profileId;
    private Collection<GrantedAuthority> authorities;

    public static CustomUserDetails buildUserDetails(User user){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getAccountType().name()));

        return new CustomUserDetails(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getProfileId(),
            grantedAuthorities
        );
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
       return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
    
}
