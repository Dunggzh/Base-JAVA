package com.its.base_java.dto.response.admin;

import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.entities.upload_file.UploadFile;
import com.its.base_java.entities.user.constant.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class AdminLoginRes {
    private int id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer roleId;
    private String roleName;
    private String accessToken;
    private String address;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Gender gender;
    private Integer agencyId;
    private String agencyName;
    private UploadFile avatar;
    private List<PermissionRes> permissions;

}
