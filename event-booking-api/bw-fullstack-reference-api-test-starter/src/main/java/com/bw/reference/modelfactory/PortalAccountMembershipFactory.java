package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.WorkspaceUser;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class PortalAccountMembershipFactory implements FactoryHelper<PortalAccountMembership> {

    @Override
    public Class<PortalAccountMembership> getEntity() {
        return PortalAccountMembership.class;
    }

    @Override
    public PortalAccountMembership apply(Faker faker, ModelFactory factory) {
        PortalAccountMembership workspaceUser = new PortalAccountMembership();
        workspaceUser.setWorkspaceUser(factory.create(WorkspaceUser.class));
        workspaceUser.setPortalAccount(factory.create(PortalAccount.class));
        workspaceUser.setStatus(GenericStatusConstant.ACTIVE);
        workspaceUser.setDateCreated(new Date());
        return workspaceUser;
    }
}
