package com.its.base_java.entities.role.role_permission;

import com.its.base_java.entities.BaseEntity;
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
@Table(name = "role_permission")
public class RolePermission extends BaseEntity {
    @Column(name = "role_id")
    int roleId;
    @Column(name = "permission_id")
    int permissionId;
    @Column(name = "is_view")
    boolean isView;
    @Column(name = "is_write")
    boolean isWrite;
    @Column(name = "is_approval")
    boolean isApproval;
    @Column(name = "is_decision")
    boolean isDecision;





}
