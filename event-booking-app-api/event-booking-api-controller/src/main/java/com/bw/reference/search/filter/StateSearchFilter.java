package com.bw.reference.search.filter;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.QState;
import lombok.Data;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@Data
public class StateSearchFilter extends PaginatedSearchFilter implements QuerydslBinderCustomizer<QState> {
    private final String COUNTRY = "countryId";

    @Override
    public void customize(QuerydslBindings bindings, QState root) {
        bindings.bind(root.name).first((path, value) -> path.containsIgnoreCase(value));
        bindings.bind(root.country.id).as(COUNTRY).first((path, value) -> path.eq(value));
        bindings.excluding(root.country, root.status); ///TODO ????
    }
}
