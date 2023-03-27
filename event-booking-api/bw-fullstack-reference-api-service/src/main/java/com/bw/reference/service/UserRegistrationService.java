package com.bw.reference.service;

import com.bw.reference.domain.account.*;
import com.bw.reference.entity.WorkspaceUser;

public interface UserRegistrationService {

    WorkspaceUser doUserRegistrationWithBwAccount(NewUserDto newUserDto);

    WorkspaceUser registerUserWithRoles(NewUserDto newUserDto);

    WorkspaceUser createDefaultUser();

    SignUpResponse doUserRegistrationWithBwAccount(UserRegistrationDto userRegistrationDto);

    WorkspaceUser verifyEmail(WorkspaceUser workspaceUser);

}
