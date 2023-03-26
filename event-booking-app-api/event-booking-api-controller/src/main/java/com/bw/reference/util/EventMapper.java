package com.bw.reference.util;

import com.bw.reference.entity.Address;
import com.bw.reference.entity.Event;
import com.bw.reference.response.pojo.BookedEventResponse;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequiredArgsConstructor
public class EventMapper {
    public List<BookedEventResponse> mapToBookedEventResponse(List<Event> events){
        List<BookedEventResponse> responseList = new ArrayList<>();

    for (Event event : events){
        BookedEventResponse response = new BookedEventResponse();
        Address address = event.getAddress();
        response.setEventName(event.getName());
        response.setDescription(event.getDescription());
        response.setStartDate(event.getCreatedAt());
        response.setEndDate(event.getEndDate());
        response.setEventVenueNumber(address.getHouseNumber());
        response.setState(String.valueOf(address.getState()));
        response.setStreet(address.getStreet());
        response.setCity(address.getTownOrCity());
        responseList.add(response);

    }
        return responseList;
    }
}
