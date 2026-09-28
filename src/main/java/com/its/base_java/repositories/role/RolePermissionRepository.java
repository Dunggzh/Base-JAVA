package com.its.base_java.repositories.role;

import com.its.base_java.entities.role.role_permission.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    RolePermission findByRoleIdAndPermissionId(int roleId, int permissionId);
}
