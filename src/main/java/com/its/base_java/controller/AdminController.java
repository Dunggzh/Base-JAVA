package com.its.base_java.controller;

import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.admin.*;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.admin.AdminLoginRes;
import com.its.base_java.dto.response.admin.AdminRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.services.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(summary = "Add admin member")
    @PostMapping("admin/v1/add-admin")
    public ResponseEntity<BaseResponse<Admin>> addAdmin(@RequestBody @Valid AddAdminReq request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.addAdmin(request)));
    }

    @Operation(summary = "Get admin list")
    @GetMapping("admin/v1/get-admins")
    public ResponseEntity<BaseResponse<List<AdminRes>>> getAdmin(@RequestParam int page,
                                                                 @RequestParam(required = false) AdminStatus status,
                                                                 @RequestParam(required = false) Integer roleId,
                                                                 @Parameter(description = "[name, email]")
                                                                 @RequestParam(required = false) String searchKeyword) {

        return ResponseEntity.ok(adminService.getAdmins(status, roleId, searchKeyword, page));
    }

    @Operation(summary = "Update account information")
    @PostMapping("admin/v1/update-admin")
    public ResponseEntity<BaseResponse<Admin>> updateAdmin(@RequestBody @Valid UpdateAdminReq request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.uppdateAdmin(request)));
    }

    @Operation(summary = "Delete account admins")
    @PostMapping("admin/v1/delete-admins")
    public ResponseEntity<BaseResponse<List<Integer>>> deleteAdmins(@RequestBody @Valid IdsRequest request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.deleteAdmins(request)));
    }

    @Operation(summary = "Edit account profile")
    @PostMapping("admin/v1/edit-profile")
    public ResponseEntity<BaseResponse<Admin>> changePassWord(@RequestBody @Valid EditProfile request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.editProfile(request)));
    }

    @Operation(summary = "Get my profile")
    @GetMapping("admin/v1/get-my-profile")
    public ResponseEntity<BaseResponse<AdminLoginRes>> getMyProfile() {
        return ResponseEntity.ok(new BaseResponse<>(adminService.getMyProfile()));
    }

}
