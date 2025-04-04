package com.shubh.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.NotificationDTO;
import com.shubh.jobportal.entity.Notification;
import com.shubh.jobportal.enums.NotificationStatus;
import com.shubh.jobportal.repo.NotificationRepository;
import com.shubh.jobportal.utitlity.Utilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository repository;

    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        notificationDTO.setId(Utilities.getNextSequence("notifications"));
        notificationDTO.setTimeStamp(LocalDateTime.now());
        notificationDTO.setStatus(NotificationStatus.UNREAD);
        repository.save(notificationDTO.toEntity());
    }

    @Override
    public List<Notification> getUnreadNotification(Long userId) {
        return repository.findByUserIdAndStatus(userId,NotificationStatus.UNREAD);
    }

     
}
