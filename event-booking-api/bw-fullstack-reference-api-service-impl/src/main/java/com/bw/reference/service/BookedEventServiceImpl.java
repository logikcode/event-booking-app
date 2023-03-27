package com.bw.reference.service;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.eventbooking.BookedEventRepository;
import com.bw.reference.dao.eventbooking.EventApplierRepository;
import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.BookedEvent;
import com.bw.reference.entity.Event;
import com.bw.reference.entity.QEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedEventServiceImpl implements BookedEventService {
    private final String DELETED = "Successfully cancelled event";
    private final EventApplierRepository applierRepository;
    private  final BookedEventRepository bookedEventRepository;
    private final EventRepository eventRepository;
    private final AppRepository appRepository;
    @Override
    public List<BookedEvent> retrieveBookedEventsByApplier(String applierEmail) {

        return  bookedEventRepository.findBookedEventByEventApplier_EmailAddress(applierEmail);
    }

    @Override
    public Event cancelBookedEvent(Long id) {

        Event events =  appRepository.startJPAQuery(QEvent.event)
                .leftJoin(QEvent.event.address).fetchJoin()
                .where(QEvent.event.id.eq(id)).fetchFirst();
        events.setStatus(GenericStatusConstant.DELETED);

        return appRepository.merge(events);
        }


    @Override
    public String updateBookedEvent(EventApplierDto applierInfo, String eventName) {
        return null;
    }

    @Override
    public BookedEvent getBookedEventByName(String name) {
        return bookedEventRepository.findBookedEventByEventNameIgnoreCase(name);
    }


}
