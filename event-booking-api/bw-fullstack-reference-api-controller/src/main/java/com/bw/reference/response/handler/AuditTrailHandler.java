package com.bw.reference.response.handler;

import com.bw.entity.AuditTrail;
import com.bw.reference.response.pojo.AuditTrailResponse;
import com.bw.reference.service.AuditTrailService;
import com.bw.reference.util.AuditTrailMapper;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.util.List;

@Named
@RequiredArgsConstructor
public class AuditTrailHandler {
    private final AuditTrailService auditTrailService;

    public List<AuditTrailResponse> handleActivityLogFetch() {
       List<AuditTrail>  logs = auditTrailService.fetchAuditTrails();

        AuditTrailMapper auditTrailMapper = new AuditTrailMapper(logs);
        return auditTrailMapper.getResult();
    }

}
