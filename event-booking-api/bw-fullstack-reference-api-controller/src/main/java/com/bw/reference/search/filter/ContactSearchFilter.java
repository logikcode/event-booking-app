package com.bw.reference.search.filter;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.QContact;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import lombok.Data;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/
@Data
public class ContactSearchFilter extends BaseSearchDto implements QuerydslBinderCustomizer<QContact> {
    private String firstName;
    private String lastName;
    private String email;

    private GenericStatusConstant status;
    private String id;

    @Override
    public void customize(QuerydslBindings bindings, QContact root) {
        bindings.bind(root.firstName).as(firstName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.lastName).as(lastName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.email).as(email).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.id).as(id).first(SimpleExpression::eq);
    }
}
