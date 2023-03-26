package com.bw.reference.domain.enumeration;

import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.reference.domain.account.RolePermissionHolder;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public enum EcadenceAdminRole implements RolePermissionHolder {

    // TODO Include appropriate permissions
    ADMIN("ADMIN", PermissionTypeConstant.VERIFY_ORGANISATION),
    SYSTEM_ADMIN("SYSTEM ADMIN", PermissionTypeConstant.DEACTIVATE_USER),
    USER_MANAGEMENT_ADMIN("USER MANAGEMENT ADMIN", PermissionTypeConstant.DEACTIVATE_USER),
    ORGANISATION_SUBSCRIPTION_ADMIN("ORGANISATION SUBSCRIPTION ADMIN", PermissionTypeConstant.VERIFY_ORGANISATION);

    private String displayName;
    private final PermissionTypeConstant[] permissionConstant;

    EcadenceAdminRole(PermissionTypeConstant... permissionConstant) {
        this.permissionConstant = permissionConstant;
    }

    EcadenceAdminRole(String name, PermissionTypeConstant... permissionConstant) {
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
