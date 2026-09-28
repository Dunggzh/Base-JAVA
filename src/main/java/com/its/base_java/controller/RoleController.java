package com.its.base_java.controller;


import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.role.AddRoleReq;
import com.its.base_java.dto.request.role.UpdateRoleReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.dto.response.role.RoleRes;
import com.its.base_java.entities.role.constant.RoleType;
import com.its.base_java.services.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Add role for Admin")
    @PostMapping("admin/v1/role/add-role")
    public ResponseEntity<BaseResponse<?>> addRoleForAdmin(@RequestBody @Valid AddRoleReq req) {
        return ResponseEntity.ok(new BaseResponse<>(roleService.addRoleForAdmin(req)));
    }

    @Operation(summary = "Update role for Admin")
    @PostMapping("admin/v1/role/update-role")
    public ResponseEntity<BaseResponse<?>> updateRoleForAdmin(@RequestBody @Valid UpdateRoleReq req) {
        return ResponseEntity.ok(new BaseResponse<>(roleService.updateRoleForAdmin(req)));
    }

    @Operation(summary = "Delete roles in the Admin")
    @PostMapping("admin/v1/role/delete-roles")
    public ResponseEntity<BaseResponse<?>> deleteRolesForAdmin(@RequestBody @Valid IdsRequest req) {
        return ResponseEntity.ok(new BaseResponse<>(roleService.deleteRolesForAdmin(req)));
    }

    @Operation(summary = "Get role")
    @GetMapping("admin/v1/role/get-roles")
    public ResponseEntity<BaseResponse<List<RoleRes>>> getRolesForAdmin(@RequestParam int page,
                                                                        @RequestParam(required = false) ActiveStatus status,
                                                                        @Parameter(description = "[name]")
                                                                        @RequestParam(required = false) String searchKeyword) {
        return ResponseEntity.ok(roleService.getRolesForAdmin(status, searchKeyword, page));
    }

    @Operation(summary = "Get detail role")
    @GetMapping("admin/v1/role/get-detail-role")
    public ResponseEntity<BaseResponse<RoleRes>> getDetailRoleForAdmin(@RequestParam(name = "role_id") int roleId) {
        return ResponseEntity.ok(new BaseResponse<>(roleService.getDetailRoleForAdmin(roleId)));
    }

    @Operation(summary = "Get permissions")
    @GetMapping("admin/v1/role/get-permissions")
    public ResponseEntity<BaseResponse<List<PermissionRes>>> getPermissionsForAdmin(@RequestParam(required = false) RoleType type) {
        return ResponseEntity.ok(new BaseResponse<>(roleService.getPermissionsForAdmin(type)));
    }



}
