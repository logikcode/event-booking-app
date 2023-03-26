package com.bw.reference.search.handler;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.BookedEvent;
import com.bw.reference.entity.QBookedEvent;
import com.bw.reference.search.filter.BookedEventSearchFilter;
import com.bw.reference.util.DateTimeUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AllArgsConstructor;

import javax.inject.Named;
import java.time.LocalTime;


@Named
@AllArgsConstructor
public class BookedEventSearchHandler {

    private final AppRepository appRepository;


    public QueryResults<BookedEvent> searchBookedEvents(BookedEventSearchFilter search) {
        JPAQuery<BookedEvent> jpaQuery = appRepository.startJPAQuery(QBookedEvent.bookedEvent)
                .leftJoin(QBookedEvent.bookedEvent.event).fetchJoin()
                .leftJoin(QBookedEvent.bookedEvent.eventApplier).fetchJoin()
                .where(QBookedEvent.bookedEvent.status.eq(GenericStatusConstant.ACTIVE));

        if(search.getEvent() != null){
            jpaQuery.where(QBookedEvent.bookedEvent.event.name.containsIgnoreCase(search.getEvent().getName())
                    .or(QBookedEvent.bookedEvent.event.name.in(search.getEvent().getName())));
        }

        if(search.getApplierFirstName() != null){
            jpaQuery.where(QBookedEvent.bookedEvent.eventApplier.firstName.containsIgnoreCase(search.getApplierFirstName()));
        }

        search.getStartDate().ifPresent(x -> jpaQuery.where(QBookedEvent.bookedEvent
                .createdAt.goe(DateTimeUtil.toDate(x, LocalTime.MIDNIGHT))));
        search.getEndDate().ifPresent(x -> jpaQuery.where(QBookedEvent.bookedEvent
                .createdAt.loe(DateTimeUtil.toDate(x, LocalTime.MAX))));

        search.getLimit().ifPresent(limit -> jpaQuery
                .limit(limit)
                .offset(search.getOffset().orElse(0)));

        jpaQuery.orderBy(QBookedEvent.bookedEvent.createdAt.desc());


        return jpaQuery.fetchResults();
    }



}