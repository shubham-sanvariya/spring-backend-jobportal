package com.shubh.jobportal.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.entity.Otp;
import com.shubh.jobportal.exception.JobPortalException;
import com.shubh.jobportal.repo.OtpRepository;
import com.shubh.jobportal.repo.UserRepository;
import com.shubh.jobportal.security.jwt.JwtHelper;
import com.shubh.jobportal.security.userDetails.CustomUserDetails;
import com.shubh.jobportal.utitlity.Data;
import com.shubh.jobportal.utitlity.Utilities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authManger;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final ProfileService profileService;
    private final UserService userService;

    private final JwtHelper jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String domainName;

    public String login(LoginRequest request) {
        Authentication auth = doAuthenticate(request.getEmail(), request.getPassword());

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        String jwtToken = jwtHelper.generateAuthToken(userDetails);


        return jwtToken;
    }

    public Authentication doAuthenticate(String usernameOrEmail, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        try {
            Authentication auth = authManger.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to Authenticate", e);
        }
    }

    public void registerUser(UserDTO userDTO) throws MessagingException {

            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new JobPortalException("USER_FOUND");
            }

            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

            userDTO.setProfileId(profileService.createProfile(userDTO.getName()));
            userDTO.setId(Utilities.getNextSequence("users"));
            userDTO.setPassword(encodedPassword); // Store the encoded version

            userRepository.save(userDTO.toEntity());

    }

    public void sendOtp(String email, String check) throws MessagingException {
        if (check.toLowerCase() == "reset") {
            userRepository.findByEmail(email)
                    .orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        }

        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true);
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setFrom(domainName);

        String generatedOTP = Utilities.generateOTP();
        Otp otp = new Otp(email, generatedOTP, LocalDateTime.now());
        otpRepository.save(otp);
        message.setText(Data.getMessageBody(generatedOTP), true);
        mailSender.send(mm);
    }

    public void verifyOTP(String email, String otp) {
        Otp otpEntity = otpRepository.findById(email).orElseThrow(() -> new JobPortalException("OTP_EXPIRED"));

        if (!otpEntity.getOtpCode().equals(otp)) {
            throw new JobPortalException("OTP_INCORRECT");
        }

        if (otpEntity.getCreationTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new JobPortalException("OTP_EXPIRED");
        }

    }
}
