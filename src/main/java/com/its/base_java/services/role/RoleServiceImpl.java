package com.its.base_java.services.role;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.role.AddRoleReq;
import com.its.base_java.dto.request.role.UpdateRoleReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.dto.response.role.RoleRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.role.constant.RoleType;
import com.its.base_java.entities.role.permisstion.Permission;
import com.its.base_java.entities.role.role_permission.RolePermission;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.repositories.permission.PermissionRepository;
import com.its.base_java.repositories.role.RolePermissionRepository;
import com.its.base_java.repositories.role.RoleRepository;
import com.its.base_java.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static com.its.base_java.util.Constants.PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    @Override
    public Role addRoleForAdmin(AddRoleReq roleReq) {
        Admin admin = getAdmin(PermissionKey.CREATE, PermissionType.ADMIN_ROLE);
        Role role = new Role();
        role.setName(roleReq.getName());
        role.setNote(roleReq.getNote());
        role.setType(roleReq.getType());
        role.setStatus(roleReq.getStatus());
        roleRepository.save(role);

        List<RolePermission> permissions = new ArrayList<>();
        for (AddRoleReq.Permission item: roleReq.getPermissions()) {
            RolePermission permission = new RolePermission();
            permission.setRoleId(role.getId());
            permission.setPermissionId(item.getId());
            permission.setView(item.isView());
            permission.setApproval(item.isApproval());
            permission.setWrite(item.isWrite());
            permission.setDecision(item.isDecision());
            permissions.add(permission);
        }
        rolePermissionRepository.saveAll(permissions);
        return role;
    }

    @Transactional
    @Override
    public Role updateRoleForAdmin(UpdateRoleReq roleReq) {
        Admin admin = getAdmin(PermissionKey.CREATE, PermissionType.ADMIN_ROLE);
        Role role = roleRepository.getRoleForAdmin(roleReq.getId());
        if (role == null) {
            throw new BusinessException(Translator.toLocale("id_not_exist"), HttpStatus.NOT_FOUND);
        }
        role.setName(roleReq.getName());
        role.setStatus(roleReq.getStatus());
        role.setNote(roleReq.getNote());
        roleRepository.save(role);

        List<RolePermission> permissions = new ArrayList<>();

        for (AddRoleReq.Permission item: roleReq.getPermissions()) {
            RolePermission permission = rolePermissionRepository.findByRoleIdAndPermissionId(role.getId(), item.getId());
            if (permission == null) {
                permission = new RolePermission();
            }
            permission.setRoleId(role.getId());
            permission.setPermissionId(item.getId());
            permission.setView(item.isView());
            permission.setApproval(item.isApproval());
            permission.setWrite(item.isWrite());
            permission.setDecision(item.isDecision());
            permissions.add(permission);
        }
        rolePermissionRepository.saveAll(permissions);
        return role;
    }

    @Override
    public List<Integer> deleteRolesForAdmin(IdsRequest req) {
        getAdmin(PermissionKey.DECISION, PermissionType.ADMIN_ROLE);
        List<Integer> roleIds = req.getIds();
        List<Integer> existingIds = roleRepository.getAllIdToCheckExist(roleIds);
        List<Integer> nonExistingIds = roleIds.stream().filter(id -> !existingIds.contains(id)).toList();
        if (!nonExistingIds.isEmpty()) {
            throw new BusinessException(Translator.toLocale("id_not_exists"), HttpStatus.BAD_REQUEST);
        }
        roleRepository.deleteRoleByIds(roleIds);

        return roleIds;
    }

    @Override
    public BaseResponse<List<RoleRes>> getRolesForAdmin(ActiveStatus status, String name, int page) {
        getAdmin(PermissionKey.READ, PermissionType.ADMIN_ROLE);
        long countRole = roleRepository.countRoles(status, name);
        List<RoleRes> listRole = roleRepository.getRoles(page, status, name);
        int pageSize = Math.toIntExact(PAGE_SIZE);
        int totalPage = (int) Math.ceil((double) countRole / pageSize);
        return new BaseResponse<>(listRole, countRole,page,pageSize,totalPage);
        // return new BaseResponse<>(listRole, countRole);
    }

    @Override
    public RoleRes getDetailRoleForAdmin(int roleId) {
        Admin admin = getAdmin(PermissionKey.READ, PermissionType.ADMIN_ROLE);
        Role role = roleRepository.getRoleForAdmin(roleId);
        RoleRes roleRes = RoleRes.convertObject(role);
        roleRes.setPermissions(roleRepository.getPermissions(roleId));
        return roleRes;
    }

    @Override
    public List<PermissionRes> getPermissionsForAdmin(RoleType type) {
        if(type == null){
            return null;
        }
        Admin admin = getAdmin(PermissionKey.READ, PermissionType.ADMIN_ROLE);
        List<Permission> permissions = permissionRepository.getPermissions(type);
        List<PermissionRes> permissionRes = new ArrayList<>();
        for (Permission item: permissions) {
            PermissionRes permission = new PermissionRes();
            permission.setId(item.getId());
            permission.setPermission(item.getPermission());
            permission.setParentPermission(item.getParentPermission());
            permission.setTitle(item.getTitle());
            permission.setIsView(item.isView());
            permission.setIsWrite(item.isWrite());
            permission.setIsApproval(item.isApproval());
            permission.setIsDecision(item.isDecision());
            permissionRes.add(permission);
        }
        return permissionRes;
    }
}
