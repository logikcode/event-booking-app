package com.bw.reference.service;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.account.PortalAccountMemberRoleRepository;
import com.bw.reference.domain.account.PortalUserRoleUpdateDto;
import com.bw.reference.entity.PortalAccountMemberRole;
import com.bw.reference.entity.PortalAccountMembership;
import com.bw.reference.entity.PortalAccountTypeRole;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@RequiredArgsConstructor
@Named
public class PortalAccountMemberRoleServiceImpl implements PortalAccountMemberRoleService {

    private final PortalAccountMemberRoleRepository roleRepository;

    @Override
    public List<PortalAccountMemberRole> updateUserRoles(PortalAccountMembership membership, PortalUserRoleUpdateDto dto, List<PortalAccountTypeRole> typeRoles) {
        List<PortalAccountMemberRole> existingMemberRolesFromDB = roleRepository.findAllByMembershipAndStatus(membership, GenericStatusConstant.ACTIVE);

        // deactivate PortalAccountMemberRole if they are not being passed in the list of roles
        List<PortalAccountMemberRole> removed = existingMemberRolesFromDB.stream().filter(x -> !dto.getRoles().contains(x.getRole().getName()))
                .peek(accountMemberRole -> {
                    accountMemberRole.setStatus(GenericStatusConstant.DEACTIVATED);
                    roleRepository.save(accountMemberRole);
                }).collect(Collectors.toList());

        List<String> existingRoleNames = existingMemberRolesFromDB.stream().map(x -> x.getRole().getName()).collect(Collectors.toList());

        List<PortalAccountMemberRole> newRoles = typeRoles.stream().filter(x -> !existingRoleNames.contains(x.getName()))
                .map(role -> {
                    PortalAccountMemberRole membershipRole = new PortalAccountMemberRole();
                    membershipRole.setDateCreated(Timestamp.from(Instant.now()));
                    membershipRole.setMembership(membership);
                    membershipRole.setRole(role);
                    membershipRole.setStatus(GenericStatusConstant.ACTIVE);
                    roleRepository.save(membershipRole);
                    return membershipRole;
                }).collect(Collectors.toList());

        existingMemberRolesFromDB.removeAll(removed);
        existingMemberRolesFromDB.addAll(newRoles);
        return existingMemberRolesFromDB;
    }

}
