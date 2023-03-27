package com.bw.reference.dao.contact;

import com.bw.reference.entity.Contact;
import com.bw.reference.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>  {

    @Query("SELECT c FROM Contact c WHERE lower(c.email)=lower(?1)")
    Optional<Contact> findByEmail(String email);

    @Query("SELECT c FROM Contact c WHERE lower(c.email)=lower(?1) and c.status = 'ACTIVE'")
    Optional<Contact> findActiveByEmail(String email);

}
