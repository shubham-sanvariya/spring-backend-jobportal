package com.shubh.jobportal.dto;

import java.time.LocalDateTime;

import com.shubh.jobportal.entity.Notification;
import com.shubh.jobportal.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String route;
    private NotificationStatus status;
    private LocalDateTime timeStamp;

    public Notification toEntity() {
        return new Notification(this.id, this.userId,this.message,this.action,this.route,this.status,this.timeStamp);
    }
}
