package com.bw.reference.search.handler;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.QState;
import com.bw.reference.entity.State;
import com.bw.reference.search.filter.StateSearchFilter;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import javax.inject.Named;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/
@Named
public class StateSearchHandler {

    private final AppRepository appRepository;

    public StateSearchHandler(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public QueryResults<State> search(StateSearchFilter search, Predicate predicate) {
        JPAQuery<State> jpaQuery = appRepository.startJPAQuery(QState.state);

        jpaQuery.where(QState.state.status.eq(GenericStatusConstant.ACTIVE));
        search.getLimit().ifPresent(limit -> jpaQuery
                .limit(limit)
                .offset(search.getOffset().orElse(0)));
        if (predicate != null) {
            jpaQuery
                    .where(predicate);
        }
        jpaQuery.orderBy(QState.state.name.asc());
        return jpaQuery.fetchResults();
    }

}
