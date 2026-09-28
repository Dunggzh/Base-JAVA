package com.its.base_java.entities.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.its.base_java.entities.BaseEntity;
import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.entities.user.constant.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "admins")
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends BaseEntity {
    String code;
    String email;
    String phone;
    String name;
    String address;

    @JsonIgnore
    String password;
    @Column(name = "role_id", columnDefinition = "int unsigned")
    Integer roleId;

    Date birthday;

    @Column(columnDefinition = "INT")
    Gender gender;

    @Column(name = "avatar_id")
    Integer avatarId;

    @Column(columnDefinition = "INT")
    AdminStatus status;

    @JsonIgnore
    boolean deleted;
}