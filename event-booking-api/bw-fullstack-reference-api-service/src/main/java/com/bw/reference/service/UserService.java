package com.bw.reference.service;

import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.domain.account.WorkspaceUserUpdateDto;

public interface UserService {

    WorkspaceUser updateUser(WorkspaceUserUpdateDto userUpdateDto);

    //PortalUserAddress setAddress(PortalUser portalUser, AddressDto addressDto);

}
