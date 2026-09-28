package com.its.base_java.exceptions;

import org.springframework.http.HttpStatus;

import com.its.base_java.dto.response.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SipException extends RuntimeException{
    
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    private String message;

    private BaseResponse<?> response;

    public SipException(String message) {
        super(message);
        this.message= message;
    }

     public SipException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public SipException(BaseResponse<?> response) {
        super(response.getMessage());
        this.response = response;
    }

    public BaseResponse<?> getResponse() {
        return response;
    }
}
