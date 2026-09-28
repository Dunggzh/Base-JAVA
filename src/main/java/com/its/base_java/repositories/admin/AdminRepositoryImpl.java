package com.its.base_java.repositories.admin;

import com.its.base_java.dto.response.admin.AdminRes;
import com.its.base_java.entities.admin.Admin;
import com.its.base_java.entities.admin.QAdmin;
import com.its.base_java.entities.admin.constant.AdminStatus;
import com.its.base_java.repositories.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.its.base_java.util.Constants.PAGE_SIZE;

@Repository
class AdminRepositoryImpl extends BaseRepository implements AdminRepositoryCustom {

    @Override
    public Admin loginByEmail(String email) {
        QAdmin qAdmin = QAdmin.admin;

        return query().from(qAdmin)
                .where(qAdmin.email.eq(email),
                        qAdmin.deleted.eq(false))
                .select(qAdmin)
                .fetchOne();
    }

    @Override
    public List<AdminRes> getAdmins(AdminStatus status, Integer roleId, String name, int page) {
        QAdmin qAdmin = QAdmin.admin;

        BooleanBuilder builder = new BooleanBuilder();
        if (roleId != null) {
            builder.and(qAdmin.roleId.eq(roleId));
        }
        if (status != null) {
            builder.and(qAdmin.status.eq(status));
        }
        if (name != null) {
            builder.andAnyOf(qAdmin.email.contains(name),
                    qAdmin.name.contains(name));
        }
        builder.and(qAdmin.deleted.eq(false));

        return query().from(qAdmin)
                .where(builder)
                .select(Projections.fields(AdminRes.class,
                        qAdmin.id, qAdmin.name, qAdmin.email, qAdmin.status,
                        qAdmin.roleId, qAdmin.updatedAt, qAdmin.createdAt))
                .offset(page * PAGE_SIZE).limit(PAGE_SIZE)
                .orderBy(qAdmin.id.desc())
                .fetch();
    }

    @Override
    public long countAdmins(AdminStatus status, Integer roleId, String name) {
        QAdmin qAdmin = QAdmin.admin;

        BooleanBuilder builder = new BooleanBuilder();
        if (roleId != null) {
            builder.and(qAdmin.roleId.eq(roleId));
        }
        if (status != null) {
            builder.and(qAdmin.status.eq(status));
        }
        if (name != null) {
            builder.andAnyOf(qAdmin.email.eq(name),
                    qAdmin.name.contains(name));
        }
        Long count = query().from(qAdmin)
                .where(builder)
                .select(qAdmin.id.count())
                .fetchOne();
        return count == null ? 0 : count;
    }

    @Override
    public Optional<Admin> getAdminById(int id) {
        QAdmin qAdmin = QAdmin.admin;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAdmin.id.eq(id));
        builder.and(qAdmin.deleted.eq(false));

        Admin admin = query().from(qAdmin)
                .where(builder)
                .select(qAdmin)
                .fetchOne();
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Integer> getAllIdToCheckExist(List<Integer> ids) {
        QAdmin qAdmin = QAdmin.admin;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAdmin.id.in(ids));
        builder.and(qAdmin.deleted.eq(false));

        return query().from(qAdmin)
                .where(builder)
                .select(qAdmin.id)
                .fetch();
    }

    @Override
    @Transactional
    public void deleteAdmins(List<Integer> Ids) {
        QAdmin qAdmin = QAdmin.admin;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qAdmin.id.in(Ids));
        builder.and(qAdmin.deleted.eq(false));

        query().update(qAdmin)
                .set(qAdmin.deleted, true)
                .where(builder)
                .execute();
    }

    @Override
    public Admin getUserByEmail(String email) {
        QAdmin qAdmin = QAdmin.admin;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAdmin.email.eq(email));
        builder.and(qAdmin.deleted.eq(false));

        return query().from(qAdmin)
                .where(builder)
                .select(qAdmin)
                .fetchOne();
    }

}
