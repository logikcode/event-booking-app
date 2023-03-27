package com.bw.reference.service;

import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.entity.BookedEvent;
import com.bw.reference.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookedEventService {
    List<BookedEvent> retrieveBookedEventsByApplier(String applierEmail);


    Event cancelBookedEvent(Long id);
    String updateBookedEvent(EventApplierDto applierInfo, String name);
    BookedEvent getBookedEventByName(String name);


}
