package com.shubh.jobportal.service;

import com.shubh.jobportal.dto.NotificationDTO;

public interface NotificationService {
    void sendNotification(NotificationDTO notificationDTO);
}
