package com.bw.reference.service;

import com.bw.reference.domain.contact.ContactDto;
import com.bw.reference.entity.Contact;

public interface ContactService {

    Contact createContact(ContactDto contactDto);
    Long totalContact();

    void softDeleteContact(Contact contact);

}
