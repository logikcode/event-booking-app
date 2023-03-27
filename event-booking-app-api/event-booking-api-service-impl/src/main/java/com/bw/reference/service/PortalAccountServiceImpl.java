package com.bw.reference.service;

import com.bw.commons.starter.SequenceGenerator;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.account.PortalAccountRepository;
import com.bw.reference.domain.account.PortalAccountDto;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.qualifier.PortalAccountCodeSequence;

import javax.inject.Named;
import javax.inject.Provider;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Named
public class PortalAccountServiceImpl implements PortalAccountService {

    private final SequenceGenerator sequenceGenerator;
    private final PortalAccountRepository portalAccountRepository;
    private final Provider<RequestPrincipal> requestPrincipal;
    private final AccountSetupService accountSetupService;

    public PortalAccountServiceImpl(@PortalAccountCodeSequence SequenceGenerator sequenceGenerator, PortalAccountRepository portalAccountRepository, Provider<RequestPrincipal> requestPrincipal, AccountSetupService accountSetupService) {
        this.sequenceGenerator = sequenceGenerator;
        this.portalAccountRepository = portalAccountRepository;
        this.requestPrincipal = requestPrincipal;
        this.accountSetupService = accountSetupService;
    }

    @Transactional
    @Override
    public PortalAccount createPortalAccount(PortalAccountDto portalAccountDto, WorkspaceUser workspaceUser) {
        PortalAccount portalAccount = new PortalAccount();
        portalAccount.setType(portalAccountDto.getType());
        portalAccount.setName(portalAccountDto.getName());
        portalAccount.setCode(sequenceGenerator.getNext());
        portalAccount.setStatus(GenericStatusConstant.ACTIVE);
        portalAccount.setDateCreated(new Date());
        portalAccount.setCreatedBy(workspaceUser);
        portalAccountRepository.save(portalAccount);
        accountSetupService.setup(portalAccount);
        return portalAccount;
    }
}
