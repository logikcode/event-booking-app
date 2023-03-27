package com.bw.reference.service;

import com.bw.reference.domain.account.PortalAccountDto;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.WorkspaceUser;

public interface PortalAccountService {

    PortalAccount createPortalAccount(PortalAccountDto portalAccountDto, WorkspaceUser workspaceUser);
}
