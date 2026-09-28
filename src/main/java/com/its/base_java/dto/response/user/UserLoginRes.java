package com.its.base_java.dto.response.user;

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
public class UserLoginRes {
    private int id;
    String code;
    private String name;
    private String phone;
    private String email;
    private Integer roleId;
    private String accessToken;
    private String address;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Gender gender;
    private Integer companyId;
    private UploadFile avatar;
    private List<PermissionRes> permissions;


}
