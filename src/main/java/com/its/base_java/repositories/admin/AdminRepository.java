package com.its.base_java.repositories.admin;

import com.its.base_java.entities.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>, AdminRepositoryCustom {
    boolean existsByEmail(String email);
}
