package com.shubh.jobportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.entity.Notification;
import com.shubh.jobportal.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId){
        return new ResponseEntity<List<Notification>>(notificationService.getUnreadNotification(userId),HttpStatus.OK);
    }

    @PatchMapping("/read/{id}")
    public ResponseEntity<String> readNotifications(@PathVariable Long id){
        notificationService.readNotification(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
}
