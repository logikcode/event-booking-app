package com.bw.reference.search.handler;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.City;
import com.bw.reference.entity.QCity;
import com.bw.reference.search.filter.CitySearchFilter;
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
public class CitySearchHandler {

    private final AppRepository appRepository;

    public CitySearchHandler(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public QueryResults<City> search(CitySearchFilter search, Predicate predicate) {
        JPAQuery<City> jpaQuery = appRepository.startJPAQuery(QCity.city);

        jpaQuery.where(QCity.city.status.eq(GenericStatusConstant.ACTIVE));
        search.getLimit().ifPresent(limit -> jpaQuery
                .limit(limit)
                .offset(search.getOffset().orElse(0)));
        if (predicate != null) {
            jpaQuery
                    .where(predicate);
        }
        jpaQuery.orderBy(QCity.city.name.asc());
        return jpaQuery.fetchResults();
    }

}
