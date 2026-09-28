package com.its.base_java.repositories.admin;


import com.its.base_java.dto.response.admin.AdminRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.admin.constant.AdminStatus;

import java.util.List;
import java.util.Optional;

interface AdminRepositoryCustom {
    Admin loginByEmail(String email);
    List<AdminRes> getAdmins(AdminStatus status, Integer roleId, String name, int page);
    long countAdmins(AdminStatus status, Integer roleId, String name);
    Optional<Admin> getAdminById(int id);
    List<Integer> getAllIdToCheckExist(List<Integer> ids);
    void deleteAdmins(List<Integer> ids);
    Admin getUserByEmail(String email);

}
