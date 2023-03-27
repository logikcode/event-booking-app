package com.bw.reference.dao.account;


import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.entity.WorkspaceUserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Repository
public interface WorkspaceUserImageRepository extends JpaRepository<WorkspaceUserImage, Long> {

    List<WorkspaceUserImage> findByWorkspaceUserAndStatus(WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    @Query("select pi from WorkspaceUserImage pi where pi.workspaceUser in ?1 and pi.status='ACTIVE'")
    List<WorkspaceUserImage> findActiveByWorkspaceUsers(List<WorkspaceUser> workspaceUser);

    Optional<WorkspaceUserImage> findFirstByWorkspaceUserAndStatus(WorkspaceUser workspaceUser, GenericStatusConstant statusConstant);

    List<WorkspaceUserImage> findByWorkspaceUser(WorkspaceUser user);

    @Query("SELECT pi from WorkspaceUserImage  pi join fetch pi.bwFile where pi.workspaceUser = ?1 and pi.status = 'ACTIVE'")
    Optional<WorkspaceUserImage> findActiveByWorkspaceUser(WorkspaceUser workspaceUser);
}
