package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Country;
import com.bw.reference.entity.State;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.UUID;

public class StateFactory implements FactoryHelper<State> {
    @Override
    public Class<State> getEntity() {
        return State.class;
    }

    @Override
    public State apply(Faker faker, ModelFactory factory) {
        State state = new State();
        state.setName(faker.address().state());
        state.setCode(UUID.randomUUID().toString());
        state.setDisplayCode(UUID.randomUUID().toString());
        state.setCountry(factory.create(Country.class));
        state.setStatus(GenericStatusConstant.ACTIVE);
        return state;
    }
}
