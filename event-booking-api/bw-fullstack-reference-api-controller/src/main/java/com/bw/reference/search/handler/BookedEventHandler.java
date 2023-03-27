package com.bw.reference.search.handler;

import com.bw.entity.BwFile;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.constant.ActivityLogActionConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.eventbooking.BookedEventRepository;
import com.bw.reference.dao.eventbooking.EventApplierRepository;
import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.*;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.search.filter.BookedEventSearchFilter;
import com.bw.reference.search.handler.BookedEventSearchHandler;
import com.bw.reference.service.AuditTrailService;
import com.bw.reference.service.BookedEventService;
import com.bw.reference.service.FileService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import javax.inject.Named;
import javax.inject.Provider;
import java.io.IOException;
import java.util.Date;

@Named
@RequiredArgsConstructor
public class BookedEventHandler {
    private final BookedEventService bookedEventService;
    private final BookedEventSearchHandler bookedEventSearchHandler;
    private final AppRepository appRepository;
    private final BookedEventRepository bookedEventRepository;
    private final EventApplierRepository applierRepository;

    private final EventRepository eventRepository;
    private final Provider<RequestPrincipal> requestPrincipalProvider;
    private final FileService fileService;
    private AuditTrailService auditTrailService;

    public QueryResults<BookedEvent> searchBookedEvents(BookedEventSearchFilter filter) {

        return bookedEventSearchHandler.searchBookedEvents(filter);
    }
    public BookedEvent deleteBookedEvent(Long id){

        BookedEvent bookedEvent = appRepository.startJPAQuery(QBookedEvent.bookedEvent)
                .leftJoin(QBookedEvent.bookedEvent.event).fetchJoin()
                .where(QBookedEvent.bookedEvent.id.eq(id)).fetchFirst();
        bookedEvent.setStatus(GenericStatusConstant.DELETED);
        BookedEvent bookedEvents1 = appRepository.merge(bookedEvent);

        auditTrailService.createActivityLog(ActivityLogActionConstant.DELETE_BOOKED_EVENT, requestPrincipalProvider.get().getIpAddress(), BookedEvent.class.getSimpleName(), String.valueOf(id),
                "booked event with" + " " + bookedEvent.getId() + " " + "has been updated", requestPrincipalProvider.get().getWorkspaceUser());
        return bookedEvents1;
    }

    public BookedEvent updateBookedEvent(EventApplierDto applierDetails ){

        Date now = new Date();

      BookedEvent dbBookedEvent =  appRepository.startJPAQuery(QBookedEvent.bookedEvent)
                .leftJoin(QEvent.event).fetchJoin()
                .where(QEvent.event.id.eq(applierDetails.getEventId())).fetchFirst();

        EventApplier persistedApplier = dbBookedEvent.getEventApplier();
        WorkspaceUser workspaceUser = persistedApplier.getWorkspaceUser();


        persistedApplier.setFirstName(applierDetails.getFirstName());
        persistedApplier.setLastName(applierDetails.getLastName());
        persistedApplier.setMiddleName(applierDetails.getMiddleName());
        persistedApplier.setEmailAddress(applierDetails.getEmailAddress());
        persistedApplier.setStatus(persistedApplier.getStatus());
        persistedApplier.setUpdatedAt(now);
        persistedApplier.setCreatedAt(persistedApplier.getCreatedAt());

        persistedApplier.setWorkspaceUser(workspaceUser);


        EventApplier persistedApplicant = appRepository.merge(persistedApplier);

        dbBookedEvent.setEventApplier(persistedApplicant);
       auditTrailService.createActivityLog(ActivityLogActionConstant.UPDATE_BOOKED_EVENTS, requestPrincipalProvider.get().getIpAddress(),
                BookedEvent.class.getSimpleName(), String.valueOf(dbBookedEvent.getId()),
                dbBookedEvent.getEvent().getName() + " was updated by " + persistedApplicant.getFirstName(), requestPrincipalProvider.get().getWorkspaceUser());

        return  appRepository.merge(dbBookedEvent);

    }

   EventApplier retrieveApplierBookedEvent(Long id){
       return appRepository.startJPAQuery(QEventApplier.eventApplier).where(QEventApplier.eventApplier.id.eq(id)).fetchFirst();
    }


}