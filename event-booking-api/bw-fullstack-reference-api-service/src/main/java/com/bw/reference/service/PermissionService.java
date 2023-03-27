package com.bw.reference.service;


import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.entity.RolePermission;
import com.bw.reference.enums.PermissionTypeConstant;


public interface PermissionService {
    RolePermission addPermission(PortalAccountTypeRole role, PermissionTypeConstant permissionConstant);
}
