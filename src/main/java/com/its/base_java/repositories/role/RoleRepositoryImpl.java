package com.its.base_java.repositories.role;

import com.its.base_java.dto.constant.ActiveStatus;
import com.its.base_java.dto.response.permission.PermissionRes;
import com.its.base_java.dto.response.role.RoleRes;
import com.its.base_java.entities.role.QRole;
import com.its.base_java.entities.role.Role;
import com.its.base_java.entities.role.constant.PermissionKey;
import com.its.base_java.entities.role.constant.PermissionType;
import com.its.base_java.entities.role.permisstion.QPermission;
import com.its.base_java.entities.role.role_permission.QRolePermission;
import com.its.base_java.repositories.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.its.base_java.util.Constants.PAGE_SIZE;

import java.util.List;


@Repository
public class RoleRepositoryImpl extends BaseRepository implements RoleRepositoryCustom {

    @Override
    public List<Integer> getAllIdToCheckExist(List<Integer> ids) {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRole.id.in(ids));
        builder.and(qRole.deleted.eq(false));

        return query().from(qRole)
                .where(builder)
                .select(qRole.id)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteRoleByIds(List<Integer> ids) {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRole.id.in(ids));
        builder.and(qRole.deleted.eq(false));

        query().update(qRole)
                .where(builder)
                .set(qRole.deleted, true)
                .execute();
    }

    @Override
    public long countRoles(ActiveStatus status, String name) {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRole.deleted.eq(false));
        if (status != null) {
            builder.and(qRole.status.eq(status));
        }
        if (!StringUtils.isEmpty(name)) {
            builder.and(qRole.name.contains(name));
        }

        Long countRole = query().from(qRole)
                .select(qRole.id.count())
                .where(builder)
                .fetchOne();
        return countRole != null ? countRole : 0;
    }

    @Override
    public List<RoleRes> getRoles(int page, ActiveStatus status, String name) {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        if (status != null) {
            builder.and(qRole.status.eq(status));
        }
        builder.and(qRole.deleted.eq(false));
        
        if (!StringUtils.isEmpty(name)) {
            builder.and(qRole.name.contains(name));
        }
        return query().from(qRole)
                .where(builder)
                .select(Projections.fields(RoleRes.class,
                        qRole.id,  qRole.name,
                        qRole.note, qRole.type, qRole.status))
                .offset(page * PAGE_SIZE).limit(PAGE_SIZE)
                .orderBy(qRole.id.desc())
                .fetch();
    }

    @Override
    public boolean existPermission(int roleId, PermissionType[] groups, PermissionKey key) {
        QRolePermission qRolePermission = QRolePermission.rolePermission;
        QPermission qPermission = new QPermission("qPermission");

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRolePermission.roleId.eq(roleId));
        builder.and(qPermission.permission.in(groups));
        builder.and(qPermission.status.in(ActiveStatus.ACTIVE));
        if (key != null) {
            builder.and(switch (key) {
                case READ -> qRolePermission.isView.eq(true);
                case CREATE -> qRolePermission.isWrite.eq(true);
                case APPROVAL -> qRolePermission.isApproval.eq(true);
                case DECISION -> qRolePermission.isDecision.eq(true);
            });
        }

        Long count = query().from(qRolePermission)
                .innerJoin(qPermission).on(qRolePermission.permissionId.eq(qPermission.id))
                .where(builder)
                .select(qRolePermission.id.count())
                .fetchOne();
        return count != null && count > 0;
    }

    @Override
    public Role getRoleForAdmin(int id) {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRole.id.eq(id));
        builder.and(qRole.deleted.eq(false));

        return query().from(qRole)
                .where(builder)
                .select(qRole)
                .fetchOne();
    }

    @Override
    public List<PermissionRes> getPermissions(int roleId) {
        QRolePermission qRolePermission = QRolePermission.rolePermission;
        QPermission qPermission = QPermission.permission1;
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPermission.status.eq(ActiveStatus.ACTIVE));
        builder.and(qPermission.deleted.eq(false));

        return query().from(qRole)
                .innerJoin(qRolePermission).on(qRole.id.eq(qRolePermission.roleId))
                .leftJoin(qPermission).on(qPermission.id.eq(qRolePermission.permissionId), qRolePermission.roleId.eq(roleId))
                .where(builder)
                .select(Projections.fields(PermissionRes.class,
                        qPermission.id, qPermission.title, qPermission.permission,
                        qPermission.type, qPermission.parentPermission,
                        qRolePermission.isView, qRolePermission.isApproval,
                        qRolePermission.isDecision, qRolePermission.isWrite))
                .fetch();
    }
}
