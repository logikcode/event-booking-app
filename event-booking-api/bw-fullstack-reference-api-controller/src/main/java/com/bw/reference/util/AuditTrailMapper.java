package com.bw.reference.util;

import com.bw.entity.AuditTrail;
import com.bw.reference.response.pojo.AuditTrailResponse;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
public class AuditTrailMapper {
    private List<AuditTrail> auditTrails = new ArrayList<>();
    public AuditTrailMapper(List<AuditTrail> auditTrails){
        this.auditTrails = auditTrails;
    }

    public List<AuditTrailResponse> getResult(){
        List<AuditTrailResponse> responseList = new ArrayList<>();
        for (AuditTrail log : auditTrails){

            StateMapper mapper = new StateMapper(log);

            AuditTrailResponse response = new AuditTrailResponse();
            response.setId(log.getRecordId());
            response.setAction(log.getAction());
            response.setActor(log.getActor());
            response.setActorUsername(log.getEntityName());
            response.setActorType(log.getActor());
            response.setDescription(log.getDescription());
            response.setRemoteIP(log.getRemoteAddress());
            response.setStatus(mapper.getStateStatus() );
            response.setDateCreated(log.getDateCreated().toString());
            responseList.add(response);
        }
        return responseList;

    }
}
