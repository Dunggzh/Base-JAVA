package com.its.base_java.controller;

import com.its.base_java.dto.request.user.AddUserReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.user.UserLoginRes;
import com.its.base_java.entities.user.User;
import com.its.base_java.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Admin add new account for business")
    @PostMapping("admin/v1/user/add-user")
    public ResponseEntity<BaseResponse<User>> addUserForAdmin(@RequestBody @Valid AddUserReq request) {
        return ResponseEntity.ok(new BaseResponse<>(userService.addUserForAdmin(request)));
    }


    @Operation(summary = "Get my profile")
    @GetMapping("v1/user/get-my-profile")
    public ResponseEntity<BaseResponse<UserLoginRes>> getMyProfile() {
        return ResponseEntity.ok(new BaseResponse<>(userService.getMyProfile()));
    }

}
