package com.bw.reference.modelfactory;

import com.bw.entity.BwFile;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class BwFileFactory implements FactoryHelper<BwFile> {

    @Override
    public Class<BwFile> getEntity() {
        return BwFile.class;
    }

    @Override
    public BwFile apply(Faker faker, ModelFactory factory) {
        BwFile workspaceUser = new BwFile();
        workspaceUser.setContentType("text/plain");
        workspaceUser.setData(faker.shakespeare().hamletQuote().getBytes());
        workspaceUser.setDateCreated(new Date());
        return workspaceUser;
    }
}
