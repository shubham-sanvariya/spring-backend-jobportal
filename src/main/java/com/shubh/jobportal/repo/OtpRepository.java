package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Otp;

public interface OtpRepository extends MongoRepository<Otp,String>{
    
}
