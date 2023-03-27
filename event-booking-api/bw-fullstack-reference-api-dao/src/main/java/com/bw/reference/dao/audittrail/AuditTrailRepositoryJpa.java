package com.bw.reference.dao.audittrail;

import com.bw.entity.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AuditTrailRepositoryJpa extends JpaRepository<AuditTrail, Long> {

}
