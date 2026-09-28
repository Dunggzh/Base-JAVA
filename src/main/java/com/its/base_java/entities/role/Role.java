package com.its.base_java.entities.role;

import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.entities.BaseEntity;
import com.its.base_java.entities.role.constant.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "roles")
public class Role extends BaseEntity {
    String name;
    String note;

    @Column(name = "type", columnDefinition = "INT")
    RoleType type;
    @Column(name = "status", columnDefinition = "INT")
    ActiveStatus status;

}
