package com.its.base_java.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminLoginReq {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
