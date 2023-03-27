package com.bw.reference.service;

import com.bw.reference.domain.account.PortalUserRoleUpdateDto;
import com.bw.reference.entity.PortalAccountMemberRole;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.PortalAccountTypeRole;

import java.util.List;

public interface PortalAccountMemberRoleService {
    List<PortalAccountMemberRole> updateUserRoles(PortalAccountMembership membership, PortalUserRoleUpdateDto dto, List<PortalAccountTypeRole> typeRoles);
}