package com.shubh.jobportal.service;

import java.util.List;

import com.shubh.jobportal.dto.NotificationDTO;
import com.shubh.jobportal.entity.Notification;

public interface NotificationService {
    void sendNotification(NotificationDTO notificationDTO);

    List<Notification> getUnreadNotification(Long userId);

    void readNotification (Long id);
}
