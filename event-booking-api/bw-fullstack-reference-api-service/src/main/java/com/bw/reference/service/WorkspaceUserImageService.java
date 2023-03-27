package com.bw.reference.service;

import com.bw.reference.domain.account.PortalUserImageDto;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.entity.WorkspaceUserImage;
import com.bw.entity.BwFile;

import java.util.List;

public interface WorkspaceUserImageService {
    WorkspaceUserImage save(WorkspaceUser portalUser, PortalUserImageDto imageDto);

    WorkspaceUserImage update(WorkspaceUser portalUser, BwFile bwFile);

    List<WorkspaceUserImage> removeUserImage(WorkspaceUser portalUser);
}
