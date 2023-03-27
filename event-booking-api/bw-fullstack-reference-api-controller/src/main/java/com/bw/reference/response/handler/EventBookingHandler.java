package com.bw.reference.response.handler;

import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.entity.BookedEvent;
import com.bw.reference.entity.Event;
import com.bw.reference.entity.EventApplier;
import com.bw.reference.entity.QEvent;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.service.BookedEventService;
import com.bw.reference.service.EventApplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import java.util.List;

@Service
@Named
@RequiredArgsConstructor
public class EventBookingHandler {

private final EventApplierService applierService;
private final BookedEventService eventService;
private final EventRepository eventRepository;
private final AppRepository appRepository;
public EventApplier bookEvent(EventApplierDto eventInfo){
    return applierService.bookEvent(eventInfo);
}
//public String updateEventBook(EventApplierDto applier ){
//    return applierService.updateBooking(applier);
//}

public BookedEvent fetchABookedEvent(String eventName){

    return eventService.getBookedEventByName(eventName);
}

//public List<BookedEvent> fetchAllApplicantBookedEvents(String applicantEmail){
//   return eventService.retrieveBookedEventsByApplier(applicantEmail);
//}



//public Event softDeleteBookedEvent(Long id){
//    return eventService.cancelBookedEvent(id);
//}
public Event getEventSingle(Long id){

    return appRepository.startJPAQuery(QEvent.event)
            .leftJoin(QEvent.event.address).fetchJoin()
            .where(QEvent.event.id.eq(id)).fetchFirst();


}


}
