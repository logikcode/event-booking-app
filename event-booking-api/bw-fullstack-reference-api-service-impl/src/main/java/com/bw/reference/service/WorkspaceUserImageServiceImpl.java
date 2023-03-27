package com.bw.reference.service;

import com.bw.entity.BwFile;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.account.WorkspaceUserImageRepository;
import com.bw.reference.domain.account.PortalUserImageDto;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.entity.WorkspaceUserImage;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@Named
public class WorkspaceUserImageServiceImpl implements WorkspaceUserImageService {

    private final WorkspaceUserImageRepository workspaceUserImageRepository;

    private final AppRepository appRepository;

    public WorkspaceUserImageServiceImpl(final WorkspaceUserImageRepository workspaceUserImageRepository, final AppRepository appRepository) {
        this.appRepository = appRepository;
        this.workspaceUserImageRepository = workspaceUserImageRepository;
    }

    @Transactional
    @Override
    public WorkspaceUserImage save(WorkspaceUser portalUser, PortalUserImageDto imageDto) {

        removeUserImage(portalUser);

        BwFile bwFile = new BwFile();
        bwFile.setContentType(StringUtils.defaultIfBlank(imageDto.getImageData().getContentType(), "image/png"));
        bwFile.setDescription("Portal user image");
        bwFile.setData(imageDto.getImageData().getData());
        bwFile.setDateCreated(new Date());

        appRepository.persist(bwFile);

        WorkspaceUserImage portalUserImage = new WorkspaceUserImage();
        portalUserImage.setWorkspaceUser(portalUser);
        portalUserImage.setDateCreated(new Date());
        portalUserImage.setStatus(GenericStatusConstant.ACTIVE);
        portalUserImage.setBwFile(bwFile);

        return workspaceUserImageRepository.save(portalUserImage);
    }

    @Transactional
    @Override
    public WorkspaceUserImage update(WorkspaceUser portalUser, BwFile bwFile) {
        removeUserImage(portalUser);
        WorkspaceUserImage portalUserImage = new WorkspaceUserImage();
        portalUserImage.setWorkspaceUser(portalUser);
        portalUserImage.setDateCreated(new Date());
        portalUserImage.setStatus(GenericStatusConstant.ACTIVE);
        portalUserImage.setBwFile(bwFile);
        return workspaceUserImageRepository.save(portalUserImage);
    }

    @Override
    @Transactional
    public List<WorkspaceUserImage> removeUserImage(WorkspaceUser workspaceUser) {
        List<WorkspaceUserImage> userImages = deactivateImages(workspaceUserImageRepository.findByWorkspaceUserAndStatus(workspaceUser, GenericStatusConstant.ACTIVE));
        return workspaceUserImageRepository.saveAll(userImages);
    }

    List<WorkspaceUserImage> deactivateImages(List<WorkspaceUserImage> userImages) {
        return userImages
                .stream()
                .filter(img -> img.getStatus().equals(GenericStatusConstant.ACTIVE))
                .map(image -> {
                    image.setStatus(GenericStatusConstant.DEACTIVATED);
                    return image;
                })
                .collect(Collectors.toList());
    }
}
