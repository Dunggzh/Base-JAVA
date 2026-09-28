package com.its.base_java.other_service.email;

import com.its.base_java.dto.request.email.EmailDetailReq;

public interface EmailService {
    void sendEmail(EmailDetailReq request);
}
