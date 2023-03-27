package com.bw.reference.service;

import com.bw.entity.AuditTrail;
import com.bw.reference.constant.ActivityLogActionConstant;
import com.bw.reference.dao.audittrail.AuditTrailRepositoryJpa;
import com.bw.reference.entity.WorkspaceUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Named
@RequiredArgsConstructor
public class AuditTrailServiceImpl implements AuditTrailService {
    private final AuditTrailRepositoryJpa auditTrailRepository;
    @Override
    public List<AuditTrail> fetchAuditTrails() {

        return auditTrailRepository.findAll();
    }

    @Override
    public Long countTotalAuditTrails() {

        return null;
    }


    @Override
    public void createActivityLog(ActivityLogActionConstant action, String ipAddress, String affectedEntityName, String affectedEntityId,
                                  String description, WorkspaceUser actor) {
        AuditTrail activityLog = new AuditTrail();
        activityLog.setRemoteAddress(ipAddress);
        activityLog.setEntityName(affectedEntityName);
        activityLog.setRecordId(affectedEntityId);
        activityLog.setDescription(StringUtils.defaultIfBlank(description, action.getDescription()));
        activityLog.setAction(action.name());

        if (actor != null) {
            activityLog.setActor(actor.getDisplayName());
        }

        activityLog.setDateCreated(new Date());


        auditTrailRepository.save(activityLog);
    }
}
