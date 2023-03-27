package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.WorkspaceUser;
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
public class PortalUserFactory implements FactoryHelper<WorkspaceUser> {
    @Override
    public Class<WorkspaceUser> getEntity() {
        return WorkspaceUser.class;
    }

    @Override
    public WorkspaceUser apply(Faker faker, ModelFactory factory) {
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setFirstName(UUID.randomUUID().toString());
        workspaceUser.setEmail(faker.internet().emailAddress());
        workspaceUser.setLastName(faker.name().lastName());
        workspaceUser.setDisplayName(String.format("%s %s", workspaceUser.getFirstName(), workspaceUser.getLastName()));
        workspaceUser.setDateCreated(new Date());
        workspaceUser.setStatus(GenericStatusConstant.ACTIVE);
        workspaceUser.setUsername(faker.internet().emailAddress());
        workspaceUser.setPhoneNumber(faker.phoneNumber().phoneNumber());
        workspaceUser.setUserId(UUID.randomUUID().toString());
        workspaceUser.setLastUpdated(new Date());
        return workspaceUser;
    }
}
