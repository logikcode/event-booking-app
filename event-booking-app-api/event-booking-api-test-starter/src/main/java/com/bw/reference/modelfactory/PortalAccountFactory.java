package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.enums.PortalAccountTypeConstant;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;
import java.util.UUID;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class PortalAccountFactory implements FactoryHelper<PortalAccount> {

    @Override
    public Class<PortalAccount> getEntity() {
        return PortalAccount.class;
    }

    @Override
    public PortalAccount apply(Faker faker, ModelFactory factory) {
        PortalAccount portalAccount = new PortalAccount();
        portalAccount.setType(PortalAccountTypeConstant.APPLICATION);
        portalAccount.setName(faker.name().firstName());
        portalAccount.setCode(UUID.randomUUID().toString());
        portalAccount.setStatus(GenericStatusConstant.ACTIVE);
        Date now = new Date();
        portalAccount.setDateCreated(now);
        portalAccount.setLastUpdated(now);
        return portalAccount;
    }
}
