package com.shubh.jobportal.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtHelper {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${JWT_TOKEN_VALIDITY}")
    private long JWT_TOKEN_VALIDITY;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build().parseClaimsJws(token)
                    .getBody();
    }
}
