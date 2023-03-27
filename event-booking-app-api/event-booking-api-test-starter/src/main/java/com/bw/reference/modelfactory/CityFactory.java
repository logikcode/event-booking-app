package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.City;
import com.bw.reference.entity.State;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.UUID;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/

public class CityFactory implements FactoryHelper<City> {
    @Override
    public Class<City> getEntity() {
        return City.class;
    }

    @Override
    public City apply(Faker faker, ModelFactory factory) {
        City city = new City();
        city.setName(faker.address().cityName());
        city.setCode(UUID.randomUUID().toString());
        city.setStatus(GenericStatusConstant.ACTIVE);
        city.setDisplayCode(UUID.randomUUID().toString());
        city.setState(factory.create(State.class));
        return city;
    }
}
