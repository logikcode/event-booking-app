package com.bw.reference.search.filter;


import com.bw.reference.entity.QCity;
import com.querydsl.core.types.dsl.StringExpression;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@Getter
@Setter
@NoArgsConstructor
public class CitySearchFilter extends PaginatedSearchFilter implements QuerydslBinderCustomizer<QCity> {
    public static final String STATE_CODE = "stateCode";
    public static final String STATE_NAME = "stateName";

    @Override
    public void customize(QuerydslBindings bindings, QCity root) {
        bindings.bind(root.name).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.state.code).as(STATE_CODE).first(StringExpression::equalsIgnoreCase);
        bindings.bind(root.state.name).as(STATE_NAME).first(StringExpression::containsIgnoreCase);
        bindings.excluding(root.state, root.status);
    }
}
