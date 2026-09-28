package com.its.base_java.services.role;

import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.role.AddRoleReq;
import com.its.base_java.dto.request.role.UpdateRoleReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.dto.response.role.RoleRes;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.RoleType;

import java.util.List;

public interface RoleService {
    Role addRoleForAdmin(AddRoleReq role);
    Role updateRoleForAdmin(UpdateRoleReq role);
    List<Integer> deleteRolesForAdmin(IdsRequest req);
    BaseResponse<List<RoleRes>> getRolesForAdmin(ActiveStatus status, String name, int page);
    RoleRes getDetailRoleForAdmin(int roleId);
    List<PermissionRes> getPermissionsForAdmin(RoleType type);



}
