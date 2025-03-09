package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Job;
import java.util.List;


public interface JobRepository extends MongoRepository<Job,Long>{
    List<Job> findByPostedBy(Long postedBy);
}
