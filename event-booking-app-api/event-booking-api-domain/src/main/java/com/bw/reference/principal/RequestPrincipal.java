package com.bw.reference.principal;


import com.bw.enums.ActorTypeConstant;
import com.bw.reference.domain.account.AccountMembershipPojo;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.PortalAccountTypeRole;
import com.bw.reference.entity.WorkspaceUser;

import java.util.Collections;
import java.util.List;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public interface RequestPrincipal {

    String AUTH_TOKEN_NAME = "authToken";

    String getUserId();

    default String getUserName() {
        return null;
    }

    default String getDisplayName() {
        return null;
    }

    default String getIpAddress() {
        return null;
    }

    default List<PortalAccountTypeRole> getRoles() {
        return Collections.emptyList();
    }

    default WorkspaceUser getWorkspaceUser() {
        return null;
    }

//    default ActorTypeConstant getType() {
//        return ActorTypeConstant.USER;
//    }

    List<AccountMembershipPojo> getAccountPermissions();

//    void enforceMembership(PortalAccount portalAccount);

    enum PrincipalType {
        GUEST, USER, CLIENT
    }

}
