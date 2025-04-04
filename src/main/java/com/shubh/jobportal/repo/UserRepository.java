package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.User;
import java.util.Optional;



public interface UserRepository extends MongoRepository<User,Long>{
    Optional<User> findByEmail(String email);
    
    Optional<User> findByName(String name);
}
