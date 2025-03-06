package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Job;

public interface JobRepository extends MongoRepository<Job,Long>{
    
}
