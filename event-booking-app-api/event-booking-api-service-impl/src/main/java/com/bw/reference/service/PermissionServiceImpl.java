package com.bw.reference.service;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.entity.RolePermission;
import com.bw.reference.enums.PermissionTypeConstant;

import javax.inject.Named;
import java.time.Instant;
import java.util.Date;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Named
public class PermissionServiceImpl implements PermissionService {
    private final AppRepository appRepository;

    public PermissionServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public RolePermission addPermission(PortalAccountTypeRole role, PermissionTypeConstant permissionConstant) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setPortalAccountTypeRole(role);
        rolePermission.setName(permissionConstant);
        rolePermission.setDateCreated(Date.from(Instant.now()));
        rolePermission.setStatus(GenericStatusConstant.ACTIVE);
        appRepository.persist(rolePermission);
        return rolePermission;
    }
}