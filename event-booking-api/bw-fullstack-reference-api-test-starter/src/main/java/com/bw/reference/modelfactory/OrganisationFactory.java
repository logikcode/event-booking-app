package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.City;
import com.bw.reference.entity.Organisation;
import com.bw.reference.entity.PortalAccount;
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
public class OrganisationFactory implements FactoryHelper<Organisation> {

    @Override
    public Class<Organisation> getEntity() {
        return Organisation.class;
    }

    @Override
    public Organisation apply(Faker faker, ModelFactory factory) {
        Organisation organisation = new Organisation();
        organisation.setName(faker.company().name());
        organisation.setCode(UUID.randomUUID().toString());
        organisation.setStatus(GenericStatusConstant.ACTIVE);
        organisation.setPortalAccount(factory.create(PortalAccount.class));
        return organisation;
    }
}