package com.bw.reference.response.handler;

import com.bw.reference.dao.contact.ContactRepository;
import com.bw.reference.domain.contact.ContactDto;
import com.bw.reference.entity.Contact;
import com.bw.reference.service.ContactService;
import lombok.RequiredArgsConstructor;
import javax.inject.Named;
import java.util.Optional;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since September, 2021
 **/
@RequiredArgsConstructor
@Named
public class ContactHandler {

    private final ContactService contactService;

    private final ContactRepository contactRepository;

    public Contact createContact(ContactDto contactDto){
        return contactService.createContact(contactDto);
    }
    public Long totalContact(){
        return contactService.totalContact();
    }

    public void deleteContact(Long contactId){
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contactService::softDeleteContact);
    }

}
