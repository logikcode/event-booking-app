package com.bw.reference.domain.account;


import com.bw.reference.enums.PermissionTypeConstant;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public interface RolePermissionHolder {
    String roleName();

    PermissionTypeConstant[] permissions();
}
