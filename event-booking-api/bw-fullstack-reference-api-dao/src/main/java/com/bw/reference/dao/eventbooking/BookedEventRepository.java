package com.bw.reference.dao.eventbooking;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.BookedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {
    BookedEvent findBookedEventByEventNameIgnoreCase(String eventName);
    BookedEvent findBookedEventByEventNameIgnoreCaseAndAndStatus(String eventName, GenericStatusConstant status);
    List<BookedEvent> findBookedEventByEventApplier_EmailAddress(String email);
}
