package com.shubh.jobportal.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.RefreshToken;

public interface RefreshTokenRepo extends MongoRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByUserId(Long userId);
}
