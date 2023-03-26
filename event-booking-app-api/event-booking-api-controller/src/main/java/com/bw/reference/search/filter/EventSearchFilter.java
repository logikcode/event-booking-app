package com.bw.reference.search.filter;


import com.bw.reference.entity.Event;
import com.bw.reference.entity.QEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class EventSearchFilter extends PaginatedSearchFilter implements QuerydslBinderCustomizer<QEvent> {

    private String name;
    private String venue;
    private String location;
    private String description;
    private LocalDate dateOfEvent;


    public Optional<LocalDate> getDateOfEvent() {
        return Optional.ofNullable(dateOfEvent);
    }

    @Override
    public void customize(QuerydslBindings bindings, QEvent root) {

    }
}


