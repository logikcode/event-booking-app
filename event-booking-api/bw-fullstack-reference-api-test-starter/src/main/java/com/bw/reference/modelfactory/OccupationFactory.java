package com.bw.reference.modelfactory;

import com.bw.enums.GenderConstant;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Address;
import com.bw.reference.entity.Contact;
import com.bw.reference.entity.Occupation;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;
import java.util.UUID;

/**
 * @author Abbas Irekeola
 * @Email abbasirekeola@gmail.com
 * 11/4/21-10:57 AM
 */

public class OccupationFactory implements FactoryHelper<Occupation> {
    @Override
    public Class<Occupation> getEntity() {
        return Occupation.class;
    }

    @Override
    public Occupation apply(Faker faker, ModelFactory factory) {
        Occupation occupation = new Occupation();
        occupation.setName(UUID.randomUUID().toString());
        occupation.setCode(UUID.randomUUID().toString());
        occupation.setStatus(GenericStatusConstant.ACTIVE);
         return occupation;
    }
}
