package com.bw.reference.service;

import com.bw.commons.starter.TimeUtil;
import com.bw.reference.dao.AppRepository;
import com.bw.reference.dao.StateRepository;
import com.bw.reference.dao.account.WorkspaceUserRepository;
import com.bw.reference.domain.account.WorkspaceUserUpdateDto;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.principal.RequestPrincipal;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.inject.Provider;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
//@DoNotExtract
@Named
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final WorkspaceUserRepository workspaceUserRepository;
    private final Provider<RequestPrincipal> requestPrincipalProvider;
    private final TimeUtil timeUtil;
    //private final AddressService addressService;
    //private final PortalUserAddressRepository portalUserAddressRepository;
    private final AppRepository appRepository;
    private final StateRepository stateRepository;

    @Override
    @Transactional
    public WorkspaceUser updateUser(WorkspaceUserUpdateDto userUpdateDto) {
        WorkspaceUser user = requestPrincipalProvider.get().getWorkspaceUser();

        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName((userUpdateDto.getLastName()));
        user.setGender(userUpdateDto.getGender());
        user.setDateOfBirth(Optional.ofNullable(userUpdateDto.getDateOfBirth())
                .map(timeUtil::toDate)
                .orElse(null));

        user.setDisplayName(String.format("%s %s", userUpdateDto.getFirstName(), userUpdateDto.getLastName()));

        return workspaceUserRepository.save(user);
    }
}
