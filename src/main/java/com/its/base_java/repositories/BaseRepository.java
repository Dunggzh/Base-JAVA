package com.its.base_java.repositories;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public class BaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected HttpServletRequest servletRequest;

    protected void flush() {
        entityManager.flush();
    }

    protected void detach(Object obj) {
        entityManager.detach(obj);
    }

    protected JPAQueryFactory query() {
        return new JPAQueryFactory(entityManager);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
