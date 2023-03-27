package com.bw.reference.controller;
import com.bw.reference.entity.BookedEvent;

import com.bw.reference.search.filter.BookedEventSearchFilter;
import com.bw.reference.search.handler.BookedEventHandler;
import com.bw.reference.search.handler.BookedEventSearchHandler;
import com.bw.reference.service.BookedEventService;
import com.bw.reference.util.PredicateExtractor;
import com.querydsl.core.QueryResults;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booked")
public class BookedEventController {

    private final BookedEventService bookedEventService;
    private final BookedEventHandler bookedEventHandler;
    private final BookedEventSearchHandler bookedEventSearchHandler;
    private final PredicateExtractor predicateExtractor;


    @GetMapping("/event")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + org.apache.http.HttpStatus.SC_OK), description = "Ok"))
    public QueryResults<BookedEvent> searchBookedEvents(BookedEventSearchFilter filter) {
        return bookedEventHandler.searchBookedEvents(filter);
    }


}