package com.its.base_java.dto.request.admin;

import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.entities.user.constant.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAdminReq {
    @NotNull(message = "ID cannot be blank")
    int id;
    Integer roleId;
    AdminStatus status;
    String email;
    String phone;
    String name;
    String address;
    @Temporal(TemporalType.DATE)
    Date birthday;
    Gender gender;
    Integer avatarId;

}
