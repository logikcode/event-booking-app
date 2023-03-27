package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.PortalAccountMemberRole;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class PortalAccountMemberRoleFactory implements FactoryHelper<PortalAccountMemberRole> {

    @Override
    public Class<PortalAccountMemberRole> getEntity() {
        return PortalAccountMemberRole.class;
    }

    @Override
    public PortalAccountMemberRole apply(Faker faker, ModelFactory factory) {
        PortalAccountMemberRole workspaceUser = new PortalAccountMemberRole();
        workspaceUser.setMembership(factory.create(PortalAccountMembership.class));
        workspaceUser.setRole(factory.create(PortalAccountTypeRole.class));
        workspaceUser.setDateCreated(new Date());
        workspaceUser.setStatus(GenericStatusConstant.ACTIVE);
        return workspaceUser;
    }
}
