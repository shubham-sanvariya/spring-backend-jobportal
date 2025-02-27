package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Otp;
import java.util.List;
import java.time.LocalDateTime;


public interface OtpRepository extends MongoRepository<Otp,String>{
    List<Otp> findByCreationTimeBefore(LocalDateTime expiryTime);
}
