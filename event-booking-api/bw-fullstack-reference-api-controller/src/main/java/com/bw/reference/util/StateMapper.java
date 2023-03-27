package com.bw.reference.util;
import com.bw.entity.AuditTrail;
import com.bw.reference.entity.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StateMapper {
    private State state;
    AuditTrail log;

    public StateMapper(AuditTrail log){
        this.log = log;
    }
   public String getStateStatus(){

       try {
          return new ObjectMapper().readValue(log.getCurrentState(), State.class).getStatus().getValue();

       } catch (JsonProcessingException jsEx){
           jsEx.getMessage();
       }
     return "";
   }






}
