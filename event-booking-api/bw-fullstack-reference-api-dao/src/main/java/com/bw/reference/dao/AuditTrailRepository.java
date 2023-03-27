package com.bw.reference.dao;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;

import javax.inject.Named;

@Named
public interface AuditTrailRepository {
    <E> JPAQuery<E> startJPAQuery(EntityPath<E> entityPath);

}
