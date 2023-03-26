package com.bw.reference.dao.account;

import com.bw.reference.entity.*;
import com.bw.enums.GenericStatusConstant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortalAccountMemberRoleRepository extends JpaRepository<PortalAccountMemberRole, Long> {

    @Query("select r FROM PortalAccountMemberRole r" +
            " JOIN FETCH r.role" +
            " JOIN FETCH r.membership m" +
            " JOIN FETCH m.portalAccount" +
            " WHERE m.workspaceUser=?1 AND r.status=?2 AND m.status=?2")
    List<PortalAccountMemberRole> findRoles(WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    @Query("select r FROM PortalAccountMemberRole r" +
            " LEFT JOIN FETCH r.role" +
            " WHERE r.membership=?1 AND r.status=?2")
    List<PortalAccountMemberRole> findAllByMembershipAndStatus(PortalAccountMembership membership, GenericStatusConstant statusConstant);

    @Query("select r FROM PortalAccountMemberRole r" +
            " JOIN FETCH r.membership m" +
            " JOIN FETCH m.workspaceUser" +
            " WHERE m.portalAccount=?1 and r.role = ?2 AND r.status= 'ACTIVE' AND m.status= 'ACTIVE' ")
    List<PortalAccountMemberRole> findRoles(PortalAccount portalAccount, PortalAccountTypeRole role, Pageable pageable);
}
