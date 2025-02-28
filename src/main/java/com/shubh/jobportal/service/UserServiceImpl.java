package com.shubh.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubh.jobportal.dto.LoginDTO;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.entity.Otp;
import com.shubh.jobportal.entity.User;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.OtpRepository;
import com.shubh.jobportal.repo.UserRepository;
import com.shubh.jobportal.utitlity.Data;
import com.shubh.jobportal.utitlity.Utilities;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final OtpRepository otpRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new JobPortalException("USER_FOUND");
        }
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())) {
            throw new JobPortalException("INVALID_LOGIN_CREDENTIALS");
        }

        return user.toDTO();
    }

    @Override
    public void sendOtp(String email) throws Exception {
       userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));

       MimeMessage mm = mailSender.createMimeMessage();
       MimeMessageHelper message = new MimeMessageHelper(mm, true);
       message.setTo(email);
       message.setSubject("Your OTP Code");

       String generatedOTP = Utilities.generateOTP();
       Otp otp = new Otp(email,generatedOTP, LocalDateTime.now());
        otpRepository.save(otp);
        message.setText(Data.getMessageBody(generatedOTP),true);
        mailSender.send(mm);
    }

    @Override
    public void verifyOTP(String email, String otp) throws Exception {
        Otp otpEntity = otpRepository.findById(email).orElseThrow(() -> new JobPortalException("OTP_EXPIRED"));

        if (!otpEntity.getOtpCode().equals(otp)) {
            throw new JobPortalException("OTP_INCORRECT");
        }

        if (otpEntity.getCreationTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
            throw new JobPortalException("OTP_EXPIRED");
        }

    }

    @Scheduled(fixedRate = 60000)
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
    public void changePassword(LoginDTO resetDto) throws JobPortalException {
       User user = userRepository.findByEmail(resetDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        
       user.setPassword(passwordEncoder.encode(resetDto.getPassword()));
        userRepository.save(user);
    }
    
    
}
