package com.its.base_java.repositories.permission;

import com.its.base_java.entities.role.constant.RoleType;
import com.its.base_java.entities.role.permisstion.Permission;

import io.lettuce.core.dynamic.annotation.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    @Query("select p from Permission p where p.type = :type and p.status = 1 and p.deleted = false")
    List<Permission> getPermissions(@Param("type") RoleType type);
}
