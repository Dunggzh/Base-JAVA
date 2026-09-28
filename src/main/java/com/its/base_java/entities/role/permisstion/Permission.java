package com.its.base_java.entities.role.permisstion;

import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.entities.BaseEntity;
import com.its.base_java.entities.role.constant.PermissionGroup;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.role.constant.RoleType;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "permissions")
public class Permission extends BaseEntity {
    String title;
    @Enumerated(value = EnumType.STRING)
    PermissionType permission;
    @Column(name = "parent_permission")
    @Enumerated(value = EnumType.STRING)
    PermissionGroup parentPermission;
    @Column(name = "is_view")
    boolean isView;
    @Column(name = "is_write")
    boolean isWrite;
    @Column(name = "is_approval")
    boolean isApproval;
    @Column(name = "is_decision")
    boolean isDecision;

    @Column(name = "type", columnDefinition = "INT")
    RoleType type;
    @Column(name="status", columnDefinition = "INT")
    ActiveStatus status;
}
