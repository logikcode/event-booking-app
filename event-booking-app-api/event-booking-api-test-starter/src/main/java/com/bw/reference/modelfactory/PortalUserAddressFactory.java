package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Address;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.entity.WorkspaceUserAddress;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/

public class PortalUserAddressFactory implements FactoryHelper<WorkspaceUserAddress> {
    @Override
    public Class<WorkspaceUserAddress> getEntity() {
        return WorkspaceUserAddress.class;
    }

    @Override
    public WorkspaceUserAddress apply(Faker faker, ModelFactory factory) {
        WorkspaceUserAddress portalUserAddress = new WorkspaceUserAddress();
        portalUserAddress.setDateCreated(new Date());
        portalUserAddress.setStatus(GenericStatusConstant.ACTIVE);
        portalUserAddress.setWorkspaceUser(factory.create(WorkspaceUser.class));
        portalUserAddress.setAddress(factory.create(Address.class));
        return portalUserAddress;
    }
}