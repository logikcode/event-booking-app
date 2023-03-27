package com.bw.reference.service;

import com.bw.entity.ActivityLog;
import com.bw.enums.ActorTypeConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.principal.ActivityLogBuilder;
import com.bw.reference.principal.RequestPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.inject.Provider;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Named
@Transactional
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final AppRepository appRepository;
    private final Provider<RequestPrincipal> requestPrincipalProvider;

    @Override
    public ActivityLog createActivityLog(ActivityLogBuilder activityLogBuilder) {
        ActivityLog activityLog = new ActivityLog();

        activityLog.setAction(activityLogBuilder.getAction());
     //   activityLog.setEntityName(activityLogBuilder.getEntityName());
        activityLog.setDescription(activityLogBuilder.getDescription());
      //  activityLog.setRecordId(activityLogBuilder.getEntityId().toString());
        activityLog.setDateCreated(Timestamp.from(Instant.now()));
        activityLog.setRemoteAddress(StringUtils.defaultIfBlank(requestPrincipalProvider.get().getIpAddress(), "0.0.0.0"));
        activityLog.setActorType(ActorTypeConstant.USER);
        activityLog.setActor(requestPrincipalProvider.get().getWorkspaceUser().getUserId());

        appRepository.persist(activityLog);

        return activityLog;
    }
}
