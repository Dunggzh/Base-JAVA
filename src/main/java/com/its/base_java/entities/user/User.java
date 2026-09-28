package com.its.base_java.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.entities.BaseEntity;
import com.its.base_java.entities.user.constant.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User extends BaseEntity {
    String code;
    String phone;
    String email;
    String name;
    String address;
    @JsonIgnore
    String password;
    Date birthday;

    @Column(name = "gender", columnDefinition = "INT")
    Gender gender;

    @Column(name = "role_id")
    Integer roleId;

    @Column(name = "avatar_id")
    Integer avatarId;

    @Column(name = "status", columnDefinition = "INT")
    ActiveStatus status;

    boolean deleted;
}
