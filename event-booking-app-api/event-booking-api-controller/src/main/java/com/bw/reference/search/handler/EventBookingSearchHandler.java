package com.bw.reference.search.handler;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.Event;
import com.bw.reference.entity.QBookedEvent;
import com.bw.reference.entity.QEvent;
import com.bw.reference.search.filter.EventBookingSearchFilter;
import com.bw.reference.util.DateTimeUtil;
import com.bw.reference.util.EventMapper;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.time.LocalTime;

@Named
@RequiredArgsConstructor
public class EventBookingSearchHandler {
    private final AppRepository appRepository;
    public QueryResults<Event> search(EventBookingSearchFilter search, Predicate predicate) {

        JPAQuery<Event> jpaQuery = appRepository.startJPAQuery(QEvent.event)
                .leftJoin(QEvent.event.address).fetchJoin()
                .where(QEvent.event.status.eq(GenericStatusConstant.ACTIVE));

        if(search.getName() != null){
            jpaQuery.where(QEvent.event.name.containsIgnoreCase(search.getName()).or(QEvent.event.name.in(search.getName())));

        }

        if(search.getLocation() != null){
            jpaQuery.where(QEvent.event.address.townOrCity.containsIgnoreCase(search.getLocation()));
        }
//        if(search.getDescription() != null){
//            jpaQuery.where(QEvent.event.description.containsIgnoreCase(search.getDescription()));
//        }

        search.getLimit().ifPresent(limit -> jpaQuery
                .limit(limit)
                .offset(search.getOffset().orElse(0)));



     search.getDateOfEvent().ifPresent(x -> jpaQuery.where(QEvent.event
                .startDate.before(DateTimeUtil.toDate(x, LocalTime.MIDNIGHT))));

        if (predicate != null) {
            jpaQuery
                    .where(predicate);
        }

        return  jpaQuery.fetchResults();



    }

}
