package com.shubh.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.NotificationDTO;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.entity.Otp;
import com.shubh.jobportal.entity.User;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.OtpRepository;
import com.shubh.jobportal.repo.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final NotificationService notificationService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Scheduled(fixedRate = 3600000)
    public void removeExpiredOTPs() {
        System.out.println("fetching expired list of otps.");
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);

        List<Otp> expiredOtps = otpRepository.findByCreationTimeBefore(expiry);

        if (!expiredOtps.isEmpty()) {
            otpRepository.deleteAll(expiredOtps);
            System.out.println("Removed "+expiredOtps.size()+" expired OTPs.");
        }
    }

    @Override
    public void changePassword(LoginRequest resetDto) {
       User user = userRepository.findByEmail(resetDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
       
       NotificationDTO notificationDTO = new NotificationDTO();
       notificationDTO.setUserId(user.getId());
       notificationDTO.setMessage("Password Reset Successfull.");
       notificationDTO.setAction("Password Reset");
       notificationService.sendNotification(notificationDTO);

       user.setPassword(passwordEncoder.encode(resetDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUserName(Long id, String updatedName) {
        UserDTO userdDto = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found by user id.")).toDTO();
        
        userdDto.setName(updatedName);

        return userRepository.save(userdDto.toEntity()).toDTO();
    }
    
    
}
