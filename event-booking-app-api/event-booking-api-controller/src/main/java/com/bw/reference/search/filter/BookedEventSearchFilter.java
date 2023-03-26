package com.bw.reference.search.filter;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Event;
import com.bw.reference.entity.EventApplier;
import com.bw.reference.entity.QBookedEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.time.LocalDate;
import java.util.Optional;


@Getter
@Setter
public class BookedEventSearchFilter extends PaginatedSearchFilter implements QuerydslBinderCustomizer<QBookedEvent> {

    private GenericStatusConstant status;
    private Long id;
    private Event event;
//    private EventApplier eventApplier;
    private String applierFirstName;
    private LocalDate endDate;
    private LocalDate startDate;
    public Optional<LocalDate> getEndDate() {
        return Optional.ofNullable(endDate);
    }
    public Optional<LocalDate> getStartDate() {
        return Optional.ofNullable(startDate);
    }
    @Override
    public void customize(QuerydslBindings bindings, QBookedEvent root) {

    }

}