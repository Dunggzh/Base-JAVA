package com.its.base_java.dto.request.admin;

import com.its.base_java.entities.admin.constant.AdminStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddAdminReq {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotNull
    private Integer roleId;
    @NotNull
    private AdminStatus status;

}