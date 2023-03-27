package com.bw.reference.service;


import com.bw.reference.entity.PortalAccountMemberRole;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.enums.PortalAccountTypeConstant;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public interface RoleService {

    PortalAccountTypeRole createRole(PortalAccountTypeConstant portalAccount, String name, String displayName);

    PortalAccountMemberRole addRole(PortalAccountMembership workspaceUser, PortalAccountTypeRole role);
}
