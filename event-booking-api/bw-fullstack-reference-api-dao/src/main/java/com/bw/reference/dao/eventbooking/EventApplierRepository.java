package com.bw.reference.dao.eventbooking;

import com.bw.reference.entity.EventApplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventApplierRepository extends JpaRepository<EventApplier, Long> {
    @Query("SELECT p FROM EventApplier p WHERE lower(p.phoneNumber)=lower(?1)")
    Optional<EventApplier> findByPhoneNumber(String phoneNumber);

    @Query("SELECT p FROM EventApplier p WHERE lower(p.emailAddress)=lower(?1) and p.status = 'ACTIVE'")
    Optional<EventApplier> findActiveByEmail(String email);
}
