package com.bw.reference.dao.account;

import com.bw.reference.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.username)=lower(?1)")
    Optional<WorkspaceUser> findByUsername(String username);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.email)=lower(?1)")
    Optional<WorkspaceUser> findByEmail(String email);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.phoneNumber)=lower(?1)")
    Optional<WorkspaceUser> findByPhoneNumber(String phoneNumber);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.email)=lower(?1) and p.status = 'ACTIVE'")
    Optional<WorkspaceUser> findActiveByEmail(String email);

    @Query("SELECT p FROM WorkspaceUser p where lower(p.userId) = lower(?1) and p.status = 'ACTIVE'")
    Optional<WorkspaceUser> findByUserId(String userId);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.email)=lower(?1) OR lower(p.phoneNumber)=lower(?2)")
    Optional<WorkspaceUser> findByEmailOrPhoneNumber(String email, String phoneNumber);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.email)=lower(?1) OR lower(p.username)=lower(?2) Or lower(p.phoneNumber)=lower(?3)")
    Optional<WorkspaceUser> findByEmailOrUsernameOrPhoneNumber(String email, String username, String phoneNumber);

    @Query("SELECT p FROM WorkspaceUser p WHERE lower(p.email)=lower(?1) OR lower(p.username)=lower(?2)")
    Optional<WorkspaceUser> findByEmailOrUsername(String email, String username);

    @Query("SELECT p FROM WorkspaceUser p WHERE p.status = 'ACTIVE' and (lower(p.userId)=lower(?1) OR lower(p.username)=lower(?1))")
    Optional<WorkspaceUser> findFirstByUserIdOrUsername(String userId);

    @Query("SELECT p FROM WorkspaceUser p where p.id in ?1 and p.status = 'ACTIVE'")
    List<WorkspaceUser> findAllActiveByIdIn(List<Long> idList);

}
