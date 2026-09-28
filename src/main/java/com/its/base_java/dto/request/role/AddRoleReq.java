package com.its.base_java.dto.request.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.entities.role.constant.RoleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddRoleReq {
    @NotBlank(message = "Tên vai trò không được để trống")
    String name;
    String note;
    @NotNull
    RoleType type;
    @NotNull
    ActiveStatus status;

    @NotNull
    @Size(min = 1)
    List<Permission> permissions;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Permission {
        @NotNull
        Integer id;
        @JsonProperty("isView")
        boolean isView;
        @JsonProperty("isWrite")
        boolean isWrite;
        @JsonProperty("isApproval")
        boolean isApproval;
        @JsonProperty("isDecision")
        boolean isDecision;
    }
}
