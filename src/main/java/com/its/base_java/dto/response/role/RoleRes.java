package com.its.base_java.dto.response.role;


import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.RoleType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRes {
    int id;
    String name;
    String note;
    RoleType type;
    ActiveStatus status;

    List<PermissionRes> permissions;

    public static RoleRes convertObject(Role role) {
        RoleRes roleRes = new RoleRes();
        roleRes.setId(role.getId());
        roleRes.setNote(role.getNote());
        roleRes.setName(role.getName());
        roleRes.setStatus(role.getStatus());
        roleRes.setType(role.getType());
        return roleRes;
    }
}
