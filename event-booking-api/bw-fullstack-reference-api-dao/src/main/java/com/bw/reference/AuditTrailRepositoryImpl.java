package com.bw.reference;

import com.bw.reference.dao.AuditTrailRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class AuditTrailRepositoryImpl implements AuditTrailRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <E> JPAQuery<E> startJPAQuery(EntityPath<E> entityPath) {
        return new JPAQuery<E>(entityManager).from(entityPath);
    }
}
