package com.its.base_java.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerLoginReq {
    private String taxCode;
    private String password;
}
