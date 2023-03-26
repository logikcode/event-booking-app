package com.bw.reference.domain.enumeration;

import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.reference.domain.account.RolePermissionHolder;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public enum OrganisationAdminRole implements RolePermissionHolder {

    ADMIN("ADMIN",
            PermissionTypeConstant.ADD_ACCOUNT_MEMBER,
            PermissionTypeConstant.REMOVE_ACCOUNT_MEMBER_ROLE,
            PermissionTypeConstant.UPDATE_ACCOUNT_MEMBER_ROLE
    );

    private String displayName;
    private final PermissionTypeConstant[] permissionConstant;

    OrganisationAdminRole(PermissionTypeConstant... permissionConstant) {
        this.permissionConstant = permissionConstant;
    }

    OrganisationAdminRole(String name, PermissionTypeConstant... permissionConstant) {
        this(permissionConstant);
        this.displayName = name;
    }

    @Override
    public String roleName() {
        return StringUtils.defaultString(displayName, name());
    }

    @Override
    public PermissionTypeConstant[] permissions() {
        return permissionConstant;
    }
}
