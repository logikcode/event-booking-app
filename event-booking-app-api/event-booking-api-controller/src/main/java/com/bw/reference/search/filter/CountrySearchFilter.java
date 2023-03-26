package com.bw.reference.search.filter;

import com.bw.reference.entity.QCountry;
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
public class CountrySearchFilter extends PaginatedSearchFilter implements QuerydslBinderCustomizer<QCountry> {
    @Override
    public void customize(QuerydslBindings bindings, QCountry root) {
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.alpha2).first(StringExpression::containsIgnoreCase);
    }

}
