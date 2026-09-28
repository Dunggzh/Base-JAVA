package com.its.base_java.repositories.user;

import com.its.base_java.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    boolean existsByPhone(String phone);
}
