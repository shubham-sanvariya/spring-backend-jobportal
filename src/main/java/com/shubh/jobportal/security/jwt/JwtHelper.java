package com.shubh.jobportal.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.shubh.jobportal.records.StringOrLongValue;
import com.shubh.jobportal.security.userDetails.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameOrEmailFromToken(String token) {
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(CustomUserDetails userDetails, Map<String, StringOrLongValue> claims) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }

    public String generateAuthToken(CustomUserDetails customUserDetails) {
        String authority = customUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User has no authorities."));
        Map<String, StringOrLongValue> claims = new HashMap<>();
        claims.put("id", new StringOrLongValue.LongValue(customUserDetails.getId()));
        claims.put("name", new StringOrLongValue.StringValue(customUserDetails.getName()));
        claims.put("accountType", new StringOrLongValue.StringValue(authority));
        claims.put("profileId", new StringOrLongValue.LongValue(customUserDetails.getProfileId()));

        return generateToken(customUserDetails, claims);
    }

    public String generateRefreshToken(CustomUserDetails customUserDetails){
        Map<String, StringOrLongValue> claims = new HashMap<>();
        claims.put("tokenType",new StringOrLongValue.StringValue("refreshToken"));

        return generateToken(customUserDetails, claims);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);

            return !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            log.error("Invalid jwt token : {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired Token : {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("This token is not supported : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("No claims found : {} ", e.getMessage());
        }

        return false;
    }
}
