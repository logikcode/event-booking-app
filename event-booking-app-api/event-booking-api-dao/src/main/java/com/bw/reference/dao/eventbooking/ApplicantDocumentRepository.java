package com.bw.reference.dao.eventbooking;

import com.bw.reference.entity.ApplicantDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantDocumentRepository extends JpaRepository<ApplicantDocument, Long> {

}
