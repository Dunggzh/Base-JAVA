package com.its.base_java.repositories.role;

import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.dto.response.role.RoleRes;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;

import java.util.List;

public interface RoleRepositoryCustom {
    List<Integer> getAllIdToCheckExist(List<Integer> ids);
    void deleteRoleByIds (List<Integer> ids);

    long countRoles(ActiveStatus status, String name);
    List<RoleRes> getRoles(int page, ActiveStatus status, String name);

    boolean existPermission(int roleId, PermissionType[] groups, PermissionKey key);

    Role getRoleForAdmin(int id);

    List<PermissionRes> getPermissions(int roleId);

}
