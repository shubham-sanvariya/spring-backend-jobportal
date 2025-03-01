package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile,Long>{
    
}
