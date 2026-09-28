package com.its.base_java.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailReq {
    private String recipient;
    private String msgBody;
    private String subject;
}
