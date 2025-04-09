package com.shubh.jobportal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.jobportal.dto.LoginRequest;
import com.shubh.jobportal.dto.LoginResponse;
import com.shubh.jobportal.dto.LoginTokenResponse;
import com.shubh.jobportal.dto.UserDTO;
import com.shubh.jobportal.security.jwt.JwtHelper;
import com.shubh.jobportal.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final JwtHelper jwtHelper;
    private final AuthService authService;

    @Value("${REFRESH_COOKIE_MAX_AGE}")
    private int REFRESH_COOKIE_MAX_AGE;

    @Value("${ACCESS_COOKIE_MAX_AGE}")
    private int ACCESS_COOKIE_MAX_AGE;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest req, HttpServletResponse response) {
        LoginTokenResponse auth = authService.login(req);

        String accessToken = auth.tokens().accessToken();
        String refreshToken = auth.tokens().refreshToken();

        Cookie accessCookie = new Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(ACCESS_COOKIE_MAX_AGE); // 15 mins

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(REFRESH_COOKIE_MAX_AGE); // 3 days

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return new ResponseEntity<>(auth.response(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) throws MessagingException {
        authService.registerUser(userDTO);
        return new ResponseEntity<>("User Registered Succesfully", HttpStatus.CREATED);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<String> sendOTP(@RequestParam @Email(message = "{user.email.invalid}") String email,
            @RequestParam String check) throws MessagingException {
        authService.sendOtp(email, check);
        return new ResponseEntity<>("OTP sent successfully.", HttpStatus.OK);
    }

    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<String> verifyOTP(@PathVariable @Email(message = "{user.email.invalid}") String email,
            @PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "{otp.invalid}") String otp) throws Exception {
        authService.verifyOTP(email, otp);
        return new ResponseEntity<>("OTP has been verified.", HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshAccessToken(@CookieValue String refreshToken,
            HttpServletResponse response) {

            String newAccessToken = authService.generateRefreshAccessToken(refreshToken);
            return new ResponseEntity<String>(newAccessToken,HttpStatus.OK);

    }
}
