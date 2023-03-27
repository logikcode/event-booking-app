package com.bw.reference.service;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.contact.ContactRepository;
import com.bw.reference.domain.account.SignUpResponse;
import com.bw.reference.domain.contact.ContactDto;
import com.bw.reference.entity.Contact;
import com.bw.reference.entity.WorkspaceUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@RequiredArgsConstructor
@Named
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Transactional
    @Override
    public Contact createContact(ContactDto contactDto) {
        //String formattedMobileNumber = phoneNumberService.formatPhoneNumber(userRegistrationDto.getMobileNumber());
        contactRepository.findByEmail(contactDto.getEmail())
                .ifPresent(contact -> {
                    if (contactDto.getEmail().equals(contact.getEmail())) {
                        throw new IllegalArgumentException(String.format("Contact with email %s already exists", contact.getEmail()));
                    }
                });
        Contact contact = new Contact();
        contact.setEmail(contactDto.getEmail());
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setStatus(GenericStatusConstant.ACTIVE);
        contact.setGender(contactDto.getGender());

        Date now = new Date();
        contact.setDateCreated(now);

        contactRepository.save(contact);

        return contact;
    }

    @Override
    public Long totalContact() {
        return contactRepository.count();
    }

    @Override
    public void softDeleteContact(Contact contact) {
        contact.setStatus(GenericStatusConstant.DELETED);
        contactRepository.save(contact);
    }

}
