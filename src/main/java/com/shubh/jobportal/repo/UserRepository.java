package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.User;

public interface UserRepository extends MongoRepository<User,String>{
    
}
