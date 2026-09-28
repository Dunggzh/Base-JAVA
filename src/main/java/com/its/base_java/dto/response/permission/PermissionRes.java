package com.its.base_java.dto.response.permission;


import com.its.base_java.entities.role.constant.PermissionGroup;
import com.its.base_java.entities.role.constant.PermissionType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionRes {
    int id;
    String title;
    PermissionType permission;
    PermissionGroup parentPermission;
    Boolean isView;
    Boolean isWrite;
    Boolean isApproval;
    Boolean isDecision;

}
