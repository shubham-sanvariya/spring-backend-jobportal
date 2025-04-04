package com.shubh.jobportal.service;

import org.springframework.stereotype.Service;

import com.shubh.jobportal.repo.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository repository;

    
}
