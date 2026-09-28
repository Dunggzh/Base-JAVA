package com.its.base_java.repositories.user;

import com.its.base_java.entities.user.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    User loginByPhone(String email);
    User getUserByPhone(String phone);
    Optional<User> findUserByPhoneAndEmail(String phone, String email);
    boolean existsByCode (String code);

    boolean existsByEmail(String email);
}
