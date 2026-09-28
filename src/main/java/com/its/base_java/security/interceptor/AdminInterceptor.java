package com.its.base_java.security.interceptor;

import com.its.base_java.component.Translator;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.exceptions.BusinessException;
import com.its.base_java.repositories.admin.AdminRepository;
import com.its.base_java.security.JwtTokenProvider;
import com.its.base_java.security.SecurityContexts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminRepository adminRepository;

    @Value("${app.jwtSecretAdmin}")
    private String jwtSecret;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        BusinessException exception = new BusinessException(Translator.toLocale("login_required"), HttpStatus.UNAUTHORIZED);
        String vendorCode = request.getHeader("Authorization");
        if (Strings.isEmpty(vendorCode)) {
            throw exception;
        }
        String[] header = vendorCode.split(" ");
        if (header.length != 2) {
            throw exception;
        }
        String token = header[1];
        if (jwtTokenProvider.validateTokenHS512(token, jwtSecret)) {
            Integer sub = Integer.valueOf(jwtTokenProvider.getSubIdFromJwtHS512(token, jwtSecret));
            SecurityContexts.newContext();
            Admin admin = adminRepository.findById(sub).orElseThrow(()-> exception);
            SecurityContexts.getContext().setData(admin);
            return true;
        }
        throw exception;
    }
}
