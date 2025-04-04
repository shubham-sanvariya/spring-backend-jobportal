package com.shubh.jobportal.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.NotificationDTO;
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
        repository.save(notificationDTO.toEntity());
    }

     
}
