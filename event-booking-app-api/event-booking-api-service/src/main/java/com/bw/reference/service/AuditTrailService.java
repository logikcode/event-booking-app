package com.bw.reference.service;

import com.bw.entity.AuditTrail;
import com.bw.reference.constant.ActivityLogActionConstant;
import com.bw.reference.entity.WorkspaceUser;

import java.util.List;

public interface AuditTrailService {
    List<AuditTrail> fetchAuditTrails();
    Long countTotalAuditTrails();
    void createActivityLog(ActivityLogActionConstant action, String ipAddress, String affectedEntityName, String affectedEntityId,
                           String description, WorkspaceUser actor);
}
