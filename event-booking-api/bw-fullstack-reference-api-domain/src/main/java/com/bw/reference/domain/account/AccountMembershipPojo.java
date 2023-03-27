package com.bw.reference.domain.account;


import com.bw.reference.entity.PortalAccount;
import com.bw.reference.enums.PermissionTypeConstant;
import com.bw.reference.enums.PortalAccountTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountMembershipPojo<T> {

    private String accountName;

    private String accountCode;

    private Long accountId;

    private PortalAccountTypeConstant accountType;

    private Set<String> roles;

    private Set<PermissionTypeConstant> permissions;

    private T otherData;

    public AccountMembershipPojo(PortalAccount portalAccount) {
        accountName = portalAccount.getName();
        accountCode = portalAccount.getCode();
        accountType = portalAccount.getType();
        accountId = portalAccount.getId();
    }
}
