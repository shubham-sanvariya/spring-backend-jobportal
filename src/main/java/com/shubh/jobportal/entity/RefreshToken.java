package com.shubh.jobportal.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
public class RefreshToken {
    
    @Id
    private Long id;
    private String userId;
    private String refreshToken;
    private Long expiration;
}
