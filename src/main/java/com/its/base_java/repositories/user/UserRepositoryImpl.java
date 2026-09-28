package com.its.base_java.repositories.user;

import com.its.base_java.entities.user.QUser;
import com.its.base_java.entities.user.User;
import com.its.base_java.repositories.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {

    @Override
    public User loginByPhone(String phone) {
        QUser qUser = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.phone.eq(phone));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
    }

    @Override
    public User getUserByPhone(String phone) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.phone.eq(phone));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
    }

    @Override
    public Optional<User> findUserByPhoneAndEmail(String phone, String email) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (phone != null) {
            builder.and(qUser.phone.eq(phone));
        }
        if (email != null) {
            builder.and(qUser.email.eq(email));
        }
        builder.and(qUser.deleted.eq(false));

        User user = query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsByCode(String code) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qUser.code.eq(code));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser.id)
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qUser.email.eq(email));
        return query().from(qUser)
                .where(builder)
                .fetchFirst() != null;
    }


}
