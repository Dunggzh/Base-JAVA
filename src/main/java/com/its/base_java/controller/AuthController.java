package com.its.base_java.controller;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.admin.ChangePasswordReq;
import com.its.base_java.dto.request.auth.AdminLoginReq;
import com.its.base_java.dto.request.auth.PartnerLoginReq;
import com.its.base_java.dto.request.auth.ForgotPasswordReq;
import com.its.base_java.dto.request.auth.UserLoginReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.admin.AdminLoginRes;
import com.its.base_java.dto.response.user.UserLoginRes;
import com.its.base_java.services.admin.AdminService;
import com.its.base_java.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private final UserService userService;

    @Operation(summary = "Admin login")
    @PostMapping("admin/v1/auth/login")
    public ResponseEntity<BaseResponse<AdminLoginRes>> loginAdmin(@RequestBody @Valid AdminLoginReq request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.login(request)));
    }

    @Operation(summary = "Admin forgot password")
    @PostMapping("admin/v1/auth/forgot-password")
    public ResponseEntity<BaseResponse<String>> forgotPasswordAdmin(@RequestBody @Valid ForgotPasswordReq request) {
        return ResponseEntity.ok(new BaseResponse<>(adminService.forgotPassword(request)));
    }

    @Operation(summary = "Business login")
    @PostMapping("v1/auth/login")
    public ResponseEntity<BaseResponse<UserLoginRes>> loginUser(@RequestBody @Valid UserLoginReq request) {
        return ResponseEntity.ok(new BaseResponse<>(userService.login(request)));
    }

    @Operation(summary = "Business forgot password")
    @PostMapping("v1/auth/forgot-password")
    public ResponseEntity<BaseResponse<String>> forgotPassword(@RequestBody @Valid ForgotPasswordReq request) {
        return ResponseEntity.ok(new BaseResponse<>(userService.forgotPassword(request)));
    }

    @Operation(summary = "Change password for myself")
    @PostMapping("admin/v1/change-password")
    public ResponseEntity<BaseResponse<String>> changePasswordAdmin(@RequestBody @Valid ChangePasswordReq request) {
        adminService.changeMyPassword(request);
        return ResponseEntity.ok(new BaseResponse<>(Translator.toLocale("label_success")));
    }
}
