package com.bw.reference.search.handler;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.Contact;
import com.bw.reference.entity.QContact;
import com.bw.reference.search.filter.ContactSearchFilter;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import javax.inject.Named;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Named
public class ContactSearchHandler {
    private final AppRepository appRepository;
    public ContactSearchHandler(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public QueryResults<Contact> search(ContactSearchFilter search, Predicate predicate) {
        JPAQuery<Contact> jpaQuery = appRepository.startJPAQuery(QContact.contact)
                .where(QContact.contact.status.eq(GenericStatusConstant.ACTIVE));

        if(search.getStatus() != null){
            jpaQuery.where(QContact.contact.status.eq(search.getStatus()));
        }

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
