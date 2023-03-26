package com.bw.reference.controller;

import com.bw.commons.security.constraint.Public;
import com.bw.reference.entity.BookedEvent;
import com.bw.reference.entity.Event;
import com.bw.reference.entity.EventApplier;
import com.bw.reference.response.handler.EventBookingHandler;
import com.bw.reference.domain.EventApplierDto;
import com.bw.reference.search.filter.EventBookingSearchFilter;
import com.bw.reference.search.handler.BookedEventHandler;
import com.bw.reference.search.handler.EventBookingSearchHandler;
import com.bw.reference.util.PredicateExtractor;
import com.querydsl.core.QueryResults;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Public
@RequiredArgsConstructor
@Tag(name = "event booking", description = "EVENT BOOKING API")
public class EventBookingController {
    private final EventBookingHandler bookingHandler;
    private final EventBookingSearchHandler bookingSearchHandler;
    private final PredicateExtractor predicateExtractor;
    private final BookedEventHandler bookedEventHandler;

    @PostMapping("/events/book")
    @Operation(summary = "create applicants", description = "create an applicant with this API.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Applicant Created", content = @Content(schema = @Schema(implementation = EventApplierDto.class))), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<EventApplier> createEventBooking(@RequestBody EventApplierDto booking){
        return ResponseEntity.ok(bookingHandler.bookEvent(booking));
    }
    @PutMapping("/events/update")
  public BookedEvent updateBookedEvent(@RequestBody EventApplierDto applier){
       return bookedEventHandler.updateBookedEvent(applier);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> retrieveEventById(@PathVariable Long id){
        return  ResponseEntity.ok(bookingHandler.getEventSingle(id));
    }


    @DeleteMapping("/events/booking/{id}")
    public BookedEvent cancelEvent(@PathVariable Long id){
        return bookedEventHandler.deleteBookedEvent(id);
    }

    @GetMapping("/search")
    @Operation(summary = "search events", description = "filter events")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + org.apache.http.HttpStatus.SC_OK), description = "Ok"))

    public QueryResults<Event> retrieveAllEvents ( EventBookingSearchFilter filter){

        return bookingSearchHandler.search(filter, predicateExtractor.getPredicate(filter));
    }

}
