package com.bw.reference.response.pojo;

import lombok.Data;

@Data
public class AuditTrailResponse {
    public String id;
    public String action = "Action";
    public String actor = "actor";
    public String actorUsername = "";
    public String actorType = "";
    public String description = "";
    public String status  = "";
    public String dateCreated = "";
    public String remoteIP = "";
}
