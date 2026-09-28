package com.its.base_java.services.admin;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.request.IdsRequest;
import com.its.base_java.dto.request.admin.*;
import com.its.base_java.dto.request.auth.AdminLoginReq;
import com.its.base_java.dto.request.auth.ForgotPasswordReq;
import com.its.base_java.dto.response.BaseResponse;
import com.its.base_java.dto.response.admin.AdminLoginRes;
import com.its.base_java.dto.response.admin.AdminRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.upload_file.UploadFile;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.repositories.admin.AdminRepository;
import com.its.base_java.repositories.media.MediaRepository;
import com.its.base_java.repositories.role.RoleRepository;
import com.its.base_java.security.JwtTokenProvider;
import com.its.base_java.services.BaseService;
import com.its.base_java.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
class AdminServiceImpl extends BaseService implements AdminService {
    private final MediaRepository mediaRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwtSecretAdmin}")
    private String jwtSecret;

    @Value("${app.jwtAdminExpirationInMs}")
    private int jwtExpirationInMs;

    private final int OTP_EXPIRY_IN_MINUTES = 5;

    @Override
    public AdminLoginRes login(AdminLoginReq request) {
        Admin admin = adminRepository.loginByEmail(request.getEmail());
        if (admin == null || !passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessException(Translator.toLocale("login_fail"), HttpStatus.UNAUTHORIZED);
        }
        if (admin.getStatus() != AdminStatus.ACTIVE) {
            throw new BusinessException(Translator.toLocale("account_not_activated"));
        }
        AdminLoginRes loginRes = getLoginRes(admin);
        loginRes.setAccessToken(jwtTokenProvider.generateTokenHS512(String.valueOf(admin.getId()), jwtSecret, jwtExpirationInMs));
        return loginRes;
    }

    @Override
    public Admin addAdmin(AddAdminReq request) {
        getAdmin(PermissionKey.CREATE, PermissionType.ADMIN_ACCOUNT);
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(Translator.toLocale("email_already_exists"));
        }
        Admin admin = new Admin();
        admin.setCode(Util.randomString(8));
        admin.setEmail(request.getEmail());
        admin.setName(request.getName());
        admin.setRoleId(request.getRoleId());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setStatus(request.getStatus());
        return adminRepository.save(admin);
    }

    @Override
    public Admin uppdateAdmin(UpdateAdminReq request) {
        getAdmin(PermissionKey.CREATE, PermissionType.ADMIN_ACCOUNT);
        Admin admin = adminRepository.getAdminById(request.getId()).orElseThrow(() -> new BusinessException(Translator.toLocale("id_not_exist")));

        if (StringUtils.isNotBlank(request.getEmail()) && !request.getEmail().equals(admin.getEmail())) {
            if (adminRepository.existsByEmail(request.getEmail())) {
                throw new BusinessException(Translator.toLocale("email_already_exists"), HttpStatus.CONFLICT);
            }
            admin.setEmail(request.getEmail());
        }
        if (StringUtils.isNotBlank(request.getName())) {
            admin.setName(request.getName());
        }
        if (StringUtils.isNotBlank(request.getAddress())) {
            admin.setAddress(request.getAddress());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            admin.setPhone(request.getPhone());
        }
        if (request.getRoleId() != null) {
            admin.setRoleId(request.getRoleId());
        }
        if (request.getStatus() != null) {
            admin.setStatus(request.getStatus());
        }
        if (request.getBirthday() != null) {
            admin.setBirthday(request.getBirthday());
        }
        if (request.getGender() != null) {
            admin.setGender(request.getGender());
        }
        return adminRepository.save(admin);
    }

    @Override
    public BaseResponse<List<AdminRes>> getAdmins(AdminStatus status, Integer roleId, String name, int page) {
        getAdmin(PermissionKey.READ, PermissionType.ADMIN_ACCOUNT);
        List<AdminRes> adminRes = adminRepository.getAdmins(status, roleId, name, page);
        long count = adminRepository.countAdmins(status, roleId, name);
        return new BaseResponse<>(adminRes, count);
    }

    @Override
    public List<Integer> deleteAdmins(IdsRequest request) {
        Admin admin = getAdmin(PermissionKey.DECISION, PermissionType.ADMIN_ACCOUNT);
        List<Integer> userIds = request.getIds();

        if (userIds.contains(admin.getId())) {
            throw new BusinessException(Translator.toLocale("cannot_delete_own_account"), HttpStatus.BAD_REQUEST);
        }

//        Check if IDs exist
        List<Integer> existingIds = adminRepository.getAllIdToCheckExist(userIds);
        List<Integer> nonExistingIds = userIds.stream()
                .filter(id -> !existingIds.contains(id))
                .toList();
        if (!nonExistingIds.isEmpty())
            throw new BusinessException(Translator.toLocale("id_not_exist"), HttpStatus.BAD_REQUEST);

        adminRepository.deleteAdmins(userIds);
        return userIds;

    }

    @Override
    public Admin editProfile(EditProfile request) {
        Admin admin = getAdmin();
        if (StringUtils.isNotBlank(request.getName())) {
            admin.setName(request.getName());
        }
        if (StringUtils.isNotBlank(request.getAddress())) {
            admin.setAddress(request.getAddress());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            admin.setPhone(request.getPhone());
        }
        if (request.getStatus() != null) {
            admin.setStatus(request.getStatus());
        }
        if (request.getBirthday() != null) {
            admin.setBirthday(request.getBirthday());
        }
        if (request.getGender() != null) {
            admin.setGender(request.getGender());
        }
        return adminRepository.save(admin);
    }

    @Override
    public String forgotPassword(ForgotPasswordReq request) {
        return "";
    }

    @Override
    public AdminLoginRes getMyProfile() {
        Admin admin = getAdmin();
        return getLoginRes(admin);
    }

    private AdminLoginRes getLoginRes(Admin admin) {
        UploadFile uploadFile = null;
        if (admin.getAvatarId() != null) {
            uploadFile = mediaRepository.findUploadFileById(admin.getAvatarId());
        }
        Role role = roleRepository.getRoleForAdmin(admin.getRoleId());
        return AdminLoginRes.builder()
                .id(admin.getId())
                .code(admin.getCode())
                .name(admin.getName())
                .email(admin.getEmail())
                .address(admin.getAddress())
                .roleId(admin.getRoleId())
                .roleName(role.getName())
                .birthday(admin.getBirthday())
                .gender(admin.getGender())
                .phone(admin.getPhone())
                .avatar(uploadFile)
                .permissions(roleRepository.getPermissions(admin.getRoleId()))
                .build();
    }

    @Override
    public void changeMyPassword(ChangePasswordReq request) {
        Admin admin = getAdmin();
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            throw new BusinessException(Translator.toLocale("password_wrong"));
        }
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);
    }
}
