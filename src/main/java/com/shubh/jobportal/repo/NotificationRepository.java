package com.shubh.jobportal.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shubh.jobportal.entity.Notification;
import com.shubh.jobportal.enums.NotificationStatus;

import java.util.List;


public interface NotificationRepository extends MongoRepository<Notification,Long>{
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);

}
