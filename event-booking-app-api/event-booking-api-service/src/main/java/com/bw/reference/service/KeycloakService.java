package com.bw.reference.service;

import com.bw.reference.domain.AuthUserDto;
import com.bw.reference.domain.account.NewUserDto;
import com.bw.reference.domain.account.SignUpResponse;
import com.bw.reference.entity.WorkspaceUser;

/**
 * @author Gibah Joseph
 * email: jgibah@byteworks.com.ng
 * Sep, 2020
 **/
public interface KeycloakService {
    AuthUserDto createNewUser(NewUserDto user);

    void changePassword(String newPassword, WorkspaceUser portalUser);

    void logout(WorkspaceUser portalUser);

    void createDefaultScopes();

    SignUpResponse createNewUser(WorkspaceUser portalUser, String password, boolean adminCreated);

    String generateAccessToken(String username, String password, String clientId);
}
