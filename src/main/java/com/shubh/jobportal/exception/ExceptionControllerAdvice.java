package com.shubh.jobportal.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private Environment environment;
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception ex){
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JobPortalException.class)
    public ResponseEntity<ErrorInfo> generalException(JobPortalException ex){
        String message = environment.getProperty(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(message, HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCookieException.class)
    public ResponseEntity<ErrorInfo> handleInvalidCookieException(InvalidCookieException ex){
        String message = environment.getProperty(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(message, HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(IllegalArgumentException ex){
        String message = environment.getProperty(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(message, HttpStatus.NOT_FOUND.value(), LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception ex){
        String message = "";
        if (ex instanceof MethodArgumentNotValidException mave) {
            message = mave.getAllErrors().stream().map((ObjectError::getDefaultMessage)).collect(Collectors.joining(", "));
        }else{
            var cve = (ConstraintViolationException) ex;
            message = cve.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo(message, HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
