package com.bw.reference.modelfactory;

import com.bw.entity.BwFile;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.entity.WorkspaceUserImage;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class WorkspaceUserImageFactory implements FactoryHelper<WorkspaceUserImage> {

    @Override
    public Class<WorkspaceUserImage> getEntity() {
        return WorkspaceUserImage.class;
    }

    @Override
    public WorkspaceUserImage apply(Faker faker, ModelFactory factory) {
        WorkspaceUserImage portalUserImage = new WorkspaceUserImage();
        portalUserImage.setStatus(GenericStatusConstant.ACTIVE);
        portalUserImage.setWorkspaceUser(factory.create(WorkspaceUser.class));
        portalUserImage.setBwFile(factory.create(BwFile.class));
        portalUserImage.setDateCreated(new Date());
        return portalUserImage;
    }

}