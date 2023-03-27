package com.bw.reference.modelfactory;

import com.bw.entity.ActivityLog;
import com.bw.enums.ActorTypeConstant;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/

public class ActivityLogFactory implements FactoryHelper<ActivityLog> {
    @Override
    public Class<ActivityLog> getEntity() {
        return ActivityLog.class;
    }

    @Override
    public ActivityLog apply(Faker faker, ModelFactory factory) {
        ActivityLog activityLog = new ActivityLog();

        activityLog.setAction(faker.lorem().toString());
        activityLog.setActor(faker.name().username());
        activityLog.setActorType(ActorTypeConstant.USER);
        activityLog.setDateCreated(new Date());
        activityLog.setDescription(faker.lorem().sentence());
    //    activityLog.setEntityName(faker.name().name());
//        activityLog.setRecordId(faker.number().digits(6));
        activityLog.setRemoteAddress(faker.internet().ipV4Address());

        return activityLog;
    }
}
