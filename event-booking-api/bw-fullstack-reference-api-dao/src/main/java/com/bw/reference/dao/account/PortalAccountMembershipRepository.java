package com.bw.reference.dao.account;

import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortalAccountMembershipRepository extends JpaRepository<PortalAccountMembership, Long> {

    @Query("select m FROM PortalAccountMembership m WHERE m.portalAccount=?1 AND m.workspaceUser=?2 AND m.status=?3")
    Optional<PortalAccountMembership> findMembership(PortalAccount portalAccount, WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    @Query("select m FROM PortalAccountMembership m WHERE m.workspaceUser=?1 AND m.status=?2")
    List<PortalAccountMembership> findMembership(WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    @Query("select m FROM PortalAccountMembership m WHERE m.workspaceUser=?1 AND m.status=?2")
    List<PortalAccountMembership> findMemberships(WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    @Query("select COUNT(m) FROM PortalAccountMembership m WHERE m.portalAccount=?1 AND m.status='ACTIVE'")
    int countActiveMemberships(PortalAccount portalAccount);

    @Query("select COUNT(DISTINCT mr.membership) FROM PortalAccountMemberRole mr" +
            " WHERE mr.membership.portalAccount=?1 AND mr.role=?2" +
            " AND mr.status='ACTIVE' AND mr.membership.status='ACTIVE'")
    int countActiveMemberships(PortalAccount portalAccount, PortalAccountTypeRole role);
}
