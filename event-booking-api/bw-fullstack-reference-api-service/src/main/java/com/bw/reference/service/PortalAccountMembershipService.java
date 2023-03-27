package com.bw.reference.service;

import com.bw.reference.domain.account.AccountMembershipPojo;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.WorkspaceUser;

import java.util.List;

public interface PortalAccountMembershipService {

    PortalAccountMembership createMembership(PortalAccount portalAccount, WorkspaceUser user);

    List<AccountMembershipPojo> getAllMemberships(WorkspaceUser workspaceUser);

    PortalAccountMembership removeMembership(PortalAccountMembership portalAccountMembership);
}
