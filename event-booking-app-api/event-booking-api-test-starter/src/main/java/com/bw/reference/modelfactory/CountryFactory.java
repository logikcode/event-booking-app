package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Country;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class CountryFactory implements FactoryHelper<Country> {
    @Override
    public Class<Country> getEntity() {
        return Country.class;
    }

    @Override
    public Country apply(Faker faker, ModelFactory factory) {
        Country country = new Country();
        country.setAlpha3(faker.random().hex());
        country.setAlpha2(faker.random().hex());
        country.setName(faker.country().name());
        country.setStatus(GenericStatusConstant.ACTIVE);
        country.setInternationalDialingCode(faker.phoneNumber().phoneNumber());
        return country;
    }
}
