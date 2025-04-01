package com.shubh.jobportal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Job;
import java.util.List;
import com.shubh.jobportal.enums.JobStatus;



public interface JobRepository extends MongoRepository<Job,Long>{
    List<Job> findByPostedBy(Long postedBy);

    Page<Job> findByJobStatus(JobStatus jobStatus, Pageable pageable);
}
