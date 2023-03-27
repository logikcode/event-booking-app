package com.bw.reference.modelfactory;

import com.bw.reference.entity.Address;
import com.bw.reference.entity.City;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.State;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class AddressFactory implements FactoryHelper<Address> {

    @Override
    public Class<Address> getEntity() {
        return Address.class;
    }

    @Override
    public Address apply(Faker faker, ModelFactory factory) {
        Address address = new Address();
        address.setStreet(faker.address().streetAddress());
        address.setPostalCode(faker.address().zipCode());
        address.setHouseNumber(faker.address().buildingNumber());
        address.setCity(factory.create(City.class));
        address.setState(factory.create(State.class));
        address.setDateCreated(new Date());
        address.setTownOrCity(faker.address().city());
        return address;
    }
}
