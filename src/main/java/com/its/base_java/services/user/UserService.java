package com.its.base_java.services.user;

import com.its.base_java.dto.request.auth.ForgotPasswordReq;
import com.its.base_java.dto.request.auth.UserLoginReq;
import com.its.base_java.dto.request.user.AddUserReq;
import com.its.base_java.dto.response.user.UserLoginRes;
import com.its.base_java.entities.user.User;

public interface UserService {
    UserLoginRes login(UserLoginReq request);
    String forgotPassword (ForgotPasswordReq request);
    UserLoginRes getMyProfile();
    User addUserForAdmin (AddUserReq request);
}
