package com.its.base_java.services;

import com.its.base_java.component.Translator;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.user.User;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.repositories.role.RoleRepository;
import com.its.base_java.security.SecurityContexts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Log4j2
public class BaseService {
    @Autowired
    private RoleRepository roleRepository;

    protected Admin getAdmin() {
        try {
            return (Admin) SecurityContexts.getContext().getData();
        } catch (Exception ignored) {
        }
        throw new BusinessException(Translator.toLocale("login_required"), HttpStatus.UNAUTHORIZED);
    }

    protected User getUser() {
        try {
            return (User) SecurityContexts.getContext().getData();
        } catch (Exception ignored) {
        }
        throw new BusinessException(Translator.toLocale("login_required"), HttpStatus.UNAUTHORIZED);
    }

    protected Admin getAdmin(PermissionKey key, PermissionType... groups) {
        Admin user = getAdmin();
        if (key == null && (groups == null || groups.length == 0)) {
            return user;
        }
        if (roleRepository.existPermission(user.getRoleId(), groups, key)) {
            return user;
        }
        throw new BusinessException(Translator.toLocale("invalid_permission"));
    }
}
