package com.bw.reference.search.handler;

import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.QCountry;
import com.bw.reference.search.filter.CountrySearchFilter;
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
public class CountrySearchHandler {

    private final AppRepository appRepository;

    public CountrySearchHandler(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public QueryResults<Country> search(CountrySearchFilter search, Predicate predicate) {
        JPAQuery<Country> jpaQuery = appRepository.startJPAQuery(QCountry.country);

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
