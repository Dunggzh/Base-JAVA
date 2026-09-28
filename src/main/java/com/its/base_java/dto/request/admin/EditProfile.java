package com.its.base_java.dto.request.admin;

import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.entities.user.constant.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditProfile {
    @NotBlank
    String name;
    AdminStatus status;
    String phone;
    String address;
    @Temporal(TemporalType.DATE)
    Date birthday;
    Gender gender;
    Integer avatarId;
}
