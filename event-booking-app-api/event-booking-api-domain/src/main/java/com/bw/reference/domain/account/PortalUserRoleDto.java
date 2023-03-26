package com.bw.reference.domain.account;

import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.reference.enums.PortalAccountTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PortalUserRoleDto {
    private PortalAccountTypeConstant accountType;
    private Set<PermissionTypeConstant> permissions;

}
