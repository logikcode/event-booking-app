package com.bw.reference.search.handler;

import com.bw.entity.AuditTrail;
import com.bw.entity.QAuditTrail;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.search.filter.AuditTrailSearchFilter;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import javax.inject.Named;
@Named
public class AuditTrailSearchHandler {
    private final AppRepository appRepository;
    public AuditTrailSearchHandler(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public QueryResults<AuditTrail> search(AuditTrailSearchFilter search, Predicate predicate) {
        JPAQuery<AuditTrail> jpaQuery = appRepository.startJPAQuery(QAuditTrail.auditTrail);

        if(search.getAction() != null){
            jpaQuery.where(QAuditTrail.auditTrail.action.containsIgnoreCase(search.getAction()));

        }
        if(search.getActor() != null){
            jpaQuery.where(QAuditTrail.auditTrail.actor.containsIgnoreCase(search.getActor()));

        }
        if(search.getEntityName() != null){
            jpaQuery.where(QAuditTrail.auditTrail.entityName.containsIgnoreCase(search.getEntityName()));

        }
        if(search.getRemoteAddress() != null){
            jpaQuery.where(QAuditTrail.auditTrail.remoteAddress.containsIgnoreCase(search.getRemoteAddress()));

        }

        jpaQuery.orderBy(QAuditTrail.auditTrail.dateCreated.desc());

        search.getLimit().ifPresent(limit -> jpaQuery
                .limit(limit)
                .offset(search.getOffset().orElse(0)));
        if (predicate != null) {
            jpaQuery
                    .where(predicate);
        }
        return jpaQuery.fetchResults();
    }

}
