package com.bw.reference.search.filter;

import com.bw.entity.QAuditTrail;
import com.querydsl.core.types.dsl.StringExpression;
import lombok.Data;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import javax.inject.Named;

@Data
@Named
public class AuditTrailSearchFilter extends BaseSearchDto implements QuerydslBinderCustomizer<QAuditTrail> {
    private String action;
    private String actor;
    private String entityName;
    private String remoteAddress;
    private String description;
    // private Long id;


    @Override
    public void customize(QuerydslBindings bindings, QAuditTrail root) {

    }
}
