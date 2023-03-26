package com.bw.reference.controller;

import com.bw.commons.security.constraint.Public;
import com.bw.reference.domain.contact.ContactDto;
import com.bw.reference.entity.Contact;
import com.bw.reference.response.handler.ContactHandler;
import com.bw.reference.response.pojo.ContactPojo;
import com.bw.reference.search.filter.ContactSearchFilter;
import com.bw.reference.search.handler.ContactSearchHandler;
import com.bw.reference.util.PredicateExtractor;
import com.querydsl.core.QueryResults;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//import org.springframework.http.HttpStatus;
//import org.apache.http.HttpStatus;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Public
@RequiredArgsConstructor
@RestController
@Tag(name = "contact", description = "Contact API")
public class ContactController {

    private final PredicateExtractor predicateExtractor;
    private final ContactSearchHandler contactSearchHandler;
    private final ContactHandler contactHandler;

    @GetMapping("/contacts")
    @Operation(summary = "search contacts", description = "filter contacts")
    @ApiResponses(value = @ApiResponse(responseCode = ("" + org.apache.http.HttpStatus.SC_OK), description = "Ok"))
    public QueryResults<Contact> searchContacts(ContactSearchFilter filter) {
        return contactSearchHandler.search(filter, predicateExtractor.getPredicate(filter));
    }

    @Transactional
    @PostMapping("/contacts")
    @Operation(summary = "create contact", description = "create a contact with this API.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Contact Created", content = @Content(schema = @Schema(implementation = ContactPojo.class))), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<Contact> createContact(@RequestBody @Valid ContactDto contactDto) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(contactHandler.createContact(contactDto));
    }

    @Transactional
    @PostMapping("/contact/total")
    @Operation(summary = "total contact", description = "get total  contact")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Total contact"), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<Long> getTotalContact() {
        return ResponseEntity.status(HttpStatus.OK).body(contactHandler.totalContact());
    }

    @Transactional
    @PostMapping("/contacts/{id}/delete")
    @Operation(summary = "delete contact", description = "delete a contact with this API.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Contact Deleted"), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<Contact> deleteContact(@PathVariable("id") Long contactId ) {
        contactHandler.deleteContact(contactId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
