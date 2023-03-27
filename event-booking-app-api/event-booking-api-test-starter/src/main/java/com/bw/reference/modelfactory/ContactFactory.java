package com.bw.reference.modelfactory;
import com.bw.reference.entity.Occupation;
import com.bw.reference.entity.Address;
import java.util.Date;
import com.bw.enums.GenderConstant;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.City;
import com.bw.reference.entity.Contact;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.State;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.UUID;

/**
 * @author Abbas Irekeola
 * @Email abbasirekeola@gmail.com
 * 10/31/21-8:20 AM
 */

public class ContactFactory implements FactoryHelper<Contact> {
    @Override
    public Class<Contact> getEntity() {
        return Contact.class;
    }

    @Override
    public Contact apply(Faker faker, ModelFactory factory) {
        Contact contact = new Contact();
        contact.setFirstName(faker.name().firstName());
        contact.setLastName(faker.name().lastName());
        contact.setDateOfBirth(new Date());
        contact.setEmail(faker.internet().emailAddress());
        contact.setPhoneNumber(faker.phoneNumber().phoneNumber());
        contact.setGender(GenderConstant.MALE);
        contact.setStatus(GenericStatusConstant.ACTIVE);
        contact.setDateCreated(new Date());
        contact.setOccupation(factory.create(Occupation.class));
        contact.setAddress(factory.create(Address.class));
        return contact;
    }
}
