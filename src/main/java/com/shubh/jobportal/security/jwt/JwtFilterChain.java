package com.shubh.jobportal.security.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter{
    
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    private String parseJwtFilter(HttpServletRequest request){
        String requestHeaders = request.getHeader("Authorization");

        if (StringUtils.hasText(requestHeaders) && requestHeaders.startsWith("Bearer ")) {
            return requestHeaders.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            String jwtToken = parseJwtFilter(request);

            if (jwtToken != null && jwtHelper.isTokenValid(jwtToken)) {
                
                String userNamrOrEmail = jwtHelper.getUsernameOrEmailFromToken(jwtToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userNamrOrEmail);

                var authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication : {} ", e.getMessage());
            throw e;
        }

        filterChain.doFilter(request,response);
    }
}
