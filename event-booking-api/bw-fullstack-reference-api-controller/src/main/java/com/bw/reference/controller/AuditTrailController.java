package com.bw.reference.controller;

import com.bw.entity.AuditTrail;
import com.bw.reference.response.handler.AuditTrailHandler;
import com.bw.reference.response.pojo.AuditTrailResponse;
import com.bw.reference.search.filter.AuditTrailSearchFilter;
import com.bw.reference.search.handler.AuditTrailSearchHandler;
import com.bw.reference.util.PredicateExtractor;
import com.querydsl.core.QueryResults;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@Tag(name = "audit trail", description = "Audit Trail API")
public class AuditTrailController {

    private  final AuditTrailHandler auditTrailHandler;
    private  final AuditTrailSearchHandler auditTrailSearchHandler;
    private final PredicateExtractor extractor;
//    @GetMapping("/logs")
//    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AuditTrail.class))), @ApiResponse(responseCode = "400", description = "Bad Request")})
//    public ResponseEntity<List<AuditTrailResponse>> fetchActivityLogs(){
//
//        return ResponseEntity.ok(auditTrailHandler.handleActivityLogFetch());
//    }

    @GetMapping("/logs")
//    @ApiResponses({@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = AuditTrail.class))), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public QueryResults<AuditTrail> fetchActivityLog(AuditTrailSearchFilter filter){

        return auditTrailSearchHandler.search(filter, extractor.getPredicate(filter));
    }

}
