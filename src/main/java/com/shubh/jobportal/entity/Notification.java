package com.shubh.jobportal.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shubh.jobportal.dto.NotificationDTO;
import com.shubh.jobportal.enums.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notification")
public class Notification {
    @Id
    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String route;
    private NotificationStatus status;
    private LocalDateTime timeStamp;

    public NotificationDTO toDto() {
        return new NotificationDTO(this.id, this.userId, this.message, this.action, this.route, this.status,
                this.timeStamp);
    }
}
