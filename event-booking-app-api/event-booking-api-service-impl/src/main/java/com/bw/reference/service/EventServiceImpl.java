package com.bw.reference.service;

import com.bw.reference.dao.eventbooking.EventRepository;
import com.bw.reference.entity.Event;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;
@RequiredArgsConstructor
@Named
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @Override
    public List<Event> fetchAllEvents() {

        return eventRepository.findAll();
    }

}
