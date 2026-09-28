package com.its.base_java.services.user;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.request.auth.ForgotPasswordReq;
import com.its.base_java.dto.request.auth.UserLoginReq;
import com.its.base_java.dto.request.user.AddUserReq;
import com.its.base_java.dto.response.user.UserLoginRes;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.upload_file.UploadFile;
import com.its.base_java.entities.user.User;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.repositories.media.MediaRepository;
import com.its.base_java.repositories.permission.PermissionRepository;
import com.its.base_java.repositories.role.RolePermissionRepository;
import com.its.base_java.repositories.role.RoleRepository;
import com.its.base_java.repositories.user.UserRepository;
import com.its.base_java.security.JwtTokenProvider;
import com.its.base_java.services.BaseService;
import com.its.base_java.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final MediaRepository mediaRepository;

    @Value("${app.jwtAdminExpirationInMs}")
    private int jwtExpirationInMs;

    private final int OTP_EXPIRY_IN_MINUTES = 5;

    @Override
    public UserLoginRes login(UserLoginReq request) {
        User user = userRepository.loginByPhone(request.getPhone());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(Translator.toLocale("login_fail"), HttpStatus.UNAUTHORIZED);
        }
        if (user.getStatus() != ActiveStatus.ACTIVE) {
            throw new BusinessException(Translator.toLocale("account_not_activated"));
        }
        UserLoginRes userLoginRes = getUserRes(user);
        userLoginRes.setAccessToken(jwtTokenProvider.generateTokenRs256(String.valueOf(user.getId()), jwtExpirationInMs));
        return userLoginRes;
    }

    @Override
    public String forgotPassword(ForgotPasswordReq request) {
        return "";
    }

    @Override
    public UserLoginRes getMyProfile() {
        User user = getUser();

        return getUserRes(user);
    }

    private String generateCode(int count) {
        String code;
        boolean exists;

        do {
            code = Util.randomString(count);
            exists = userRepository.existsByCode(code);
        } while (exists);
        return code;
    }

    private UserLoginRes getUserRes(User user) {
        UploadFile uploadFile = null;
        if (user.getAvatarId() != null) {
            uploadFile = mediaRepository.findUploadFileById(user.getAvatarId());
        }
        return UserLoginRes.builder()
                .id(user.getId())
                .code(user.getCode())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .avatar(uploadFile)
                .permissions(roleRepository.getPermissions(user.getRoleId()))
                .build();
    }

    @Override
    public User addUserForAdmin(AddUserReq request) {
        getAdmin(PermissionKey.CREATE, PermissionType.ADMIN_ACCOUNT);
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BusinessException(Translator.toLocale("phone_already_exists"), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setCode(generateCode(8));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus(ActiveStatus.ACTIVE);
        user.setRoleId(request.getRoleId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }


}
