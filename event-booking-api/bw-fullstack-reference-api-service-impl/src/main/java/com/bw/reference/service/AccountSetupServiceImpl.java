package com.bw.reference.service;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.account.PortalAccountTypeRoleRepository;
import com.bw.reference.dao.account.RolePermissionRepository;
import com.bw.reference.domain.account.RolePermissionHolder;
import com.bw.reference.domain.enumeration.EcadenceAdminRole;
import com.bw.reference.domain.enumeration.OrganisationAdminRole;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.reference.enums.PortalAccountTypeConstant;

import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Named
public class AccountSetupServiceImpl implements AccountSetupService {

    private final PortalAccountTypeRoleRepository portalAccountTypeRoleRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RolePermissionRepository rolePermissionRepository;

    public AccountSetupServiceImpl(PortalAccountTypeRoleRepository portalAccountTypeRoleRepository, RoleService roleService, PermissionService permissionService, RolePermissionRepository rolePermissionRepository) {
        this.portalAccountTypeRoleRepository = portalAccountTypeRoleRepository;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.rolePermissionRepository = rolePermissionRepository;
    }


    @Override
    public void setup(PortalAccount portalAccount) {
        if (portalAccount.getType() == PortalAccountTypeConstant.APPLICATION) {
            prepareRoles(portalAccount, EcadenceAdminRole.values());
        } else if (portalAccount.getType() == PortalAccountTypeConstant.ORGANISATION) {
            prepareRoles(portalAccount, OrganisationAdminRole.values());
        }
    }

    @Transactional
    public void prepareRoles(PortalAccount portalAccount, RolePermissionHolder[] accountRoles) {
        for (RolePermissionHolder roleDes : accountRoles) {
            PortalAccountTypeRole role = portalAccountTypeRoleRepository.findActiveByPortalAccountTypeAndName(portalAccount.getType(), roleDes.roleName())
                    .orElseGet(() -> roleService.createRole(portalAccount.getType(), roleDes.roleName(), roleDes.roleName()));

            for (PermissionTypeConstant permissionConstant : roleDes.permissions()) {
                rolePermissionRepository.findByRoleAndAccountTypeAndStatus(role,
                        permissionConstant,
                        GenericStatusConstant.ACTIVE)
                        .orElseGet(() -> permissionService.addPermission(role, permissionConstant));
            }
        }
    }
}
