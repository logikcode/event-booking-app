package com.bw.reference.response.handler;

import com.bw.reference.domain.account.SignUpResponse;
import com.bw.reference.domain.account.UserRegistrationDto;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.response.pojo.UserPojo;
import com.bw.reference.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/

@RequiredArgsConstructor
@Named
public class UserRegistrationHandler {
    private final UserRegistrationService userRegistrationService;

    private final UserHandler userHandler;

//    private final EmailNotificationService emailNotificationService;

    public UserPojo registerUser(UserRegistrationDto userRegistrationDto) {
        SignUpResponse signUpResponse = registerNewUser(userRegistrationDto);
        WorkspaceUser workspaceUser = signUpResponse.getWorkspaceUser();
        UserPojo userPojo = new UserPojo(workspaceUser);
        userHandler.setDetails(workspaceUser, userPojo);
        userPojo.setAuthToken(signUpResponse.getAuthToken());
        return userPojo;
    }

    public SignUpResponse registerNewUser(UserRegistrationDto userRegistrationDto) {
        SignUpResponse signUpResponse = userRegistrationService.doUserRegistrationWithBwAccount(userRegistrationDto);
        WorkspaceUser workspaceUser = signUpResponse.getWorkspaceUser();
//        emailNotificationService.sendEmailVerification(portalUser);
        return signUpResponse;
    }
}
