package com.its.base_java.services.admin;

import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.admin.*;
import com.its.base_java.dto.request.auth.AdminLoginReq;
import com.its.base_java.dto.request.auth.ForgotPasswordReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.admin.AdminLoginRes;
import com.its.base_java.dto.response.admin.AdminRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.admin.constant.AdminStatus;

import java.util.List;

public interface AdminService {
    AdminLoginRes login(AdminLoginReq request);

    Admin addAdmin(AddAdminReq request);

    Admin uppdateAdmin(UpdateAdminReq request);

    List<Integer> deleteAdmins(IdsRequest request);

    BaseResponse<List<AdminRes>> getAdmins(AdminStatus status, Integer roleId, String name, int page);

    Admin editProfile(EditProfile request);

    String forgotPassword (ForgotPasswordReq request);

    AdminLoginRes getMyProfile();

    void changeMyPassword(ChangePasswordReq request);
}
