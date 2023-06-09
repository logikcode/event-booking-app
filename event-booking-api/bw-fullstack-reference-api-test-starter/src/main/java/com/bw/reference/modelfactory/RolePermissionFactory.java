package com.bw.reference.modelfactory;

import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.entity.RolePermission;
import com.bw.reference.enums.PermissionTypeConstant;
import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;

import java.util.Date;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@gmail.com
 * @since August, 2020
 **/
public class RolePermissionFactory implements FactoryHelper<RolePermission> {
    @Override
    public Class<RolePermission> getEntity() {
        return RolePermission.class;
    }

    @Override
    public RolePermission apply(Faker faker, ModelFactory factory) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setName(PermissionTypeConstant.UPDATE_ACCOUNT_MEMBER_ROLE);
        rolePermission.setDateCreated(new Date());
        rolePermission.setStatus(GenericStatusConstant.ACTIVE);
        rolePermission.setPortalAccountTypeRole(factory.create(PortalAccountTypeRole.class));
        return rolePermission;
    }
}