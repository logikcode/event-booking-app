package com.bw.reference.domain.account;

import com.bw.reference.enums.PermissionTypeConstant;

import java.util.List;

public class RolePermissionDto {
    private String role;
    private List<PermissionTypeConstant> permissions;

    public String getRole() {
        return role;
    }

    public com.bw.reference.domain.account.RolePermissionDto setRole(String role) {
        this.role = role;
        return this;
    }

    public List<PermissionTypeConstant> getPermissions() {
        return permissions;
    }

    public com.bw.reference.domain.account.RolePermissionDto setPermissions(List<PermissionTypeConstant> permissions) {
        this.permissions = permissions;
        return this;
    }
}
