package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.enums.PortalAccountTypeConstant;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class PortalAccountTypeRoleFactory implements FactoryHelper<PortalAccountTypeRole> {

    @Override
    public Class<PortalAccountTypeRole> getEntity() {
        return PortalAccountTypeRole.class;
    }

    @Override
    public PortalAccountTypeRole apply(Faker faker, ModelFactory factory) {
        PortalAccountTypeRole role = new PortalAccountTypeRole();
        role.setName(faker.idNumber().valid());
        role.setDisplayName(role.getName());
        role.setStatus(GenericStatusConstant.ACTIVE);
        role.setAccountType(PortalAccountTypeConstant.APPLICATION);
        Date now = new Date();
        role.setDateCreated(now);
        return role;
    }
}
