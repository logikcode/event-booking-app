package com.bw.reference.search.filter;

import com.bw.reference.entity.QEvent;
import com.querydsl.core.types.dsl.StringExpression;
import lombok.Data;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class EventBookingSearchFilter extends BaseSearchDto implements QuerydslBinderCustomizer<QEvent> {
    private String name;
    private String venue;
    private String location;
    private String description;
   private LocalDate dateOfEvent;
    @Override
    public void customize(QuerydslBindings bindings, QEvent root) {
        bindings.bind(root.name).as("name").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.venue).as("venue").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.address.townOrCity).as("venue").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.description).as("description").first(StringExpression::containsIgnoreCase);
    }
    public Optional<LocalDate> getDateOfEvent() {
        return Optional.ofNullable(dateOfEvent);
    }
}
