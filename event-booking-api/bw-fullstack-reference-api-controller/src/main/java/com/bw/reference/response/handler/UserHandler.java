package com.bw.reference.response.handler;

import com.bw.commons.authclient.dto.ApiResourcePortalUser;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.response.pojo.UserPojo;
import com.bw.reference.service.PortalAccountMembershipService;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@RequiredArgsConstructor
@Named
public class UserHandler {
    private final PortalAccountMembershipService portalAccountMembershipService;

    public UserPojo getUserPojo(WorkspaceUser portalUser, ApiResourcePortalUser apiResourcePortalUser) {
        UserPojo userPojo = new UserPojo(portalUser, apiResourcePortalUser);
        setDetails(portalUser, userPojo);
        return userPojo;
    }

    public UserPojo getUserPojo(WorkspaceUser workspaceUser) {
        UserPojo userPojo = new UserPojo(workspaceUser);
        setDetails(workspaceUser, userPojo);
        return userPojo;
    }

    public void setDetails(WorkspaceUser portalUser, UserPojo userPojo) {
        userPojo.setAccounts(portalAccountMembershipService.getAllMemberships(portalUser));
    }
}
