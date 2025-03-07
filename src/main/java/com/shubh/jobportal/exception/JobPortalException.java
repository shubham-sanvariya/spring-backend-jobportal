package com.shubh.jobportal.exception;

public class JobPortalException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public JobPortalException(String message){
        super(message);
    }
}
