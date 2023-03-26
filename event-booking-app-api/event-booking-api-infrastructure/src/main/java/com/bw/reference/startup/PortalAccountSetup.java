package com.bw.reference.startup;

import com.bw.reference.dao.account.PortalAccountMembershipRepository;
import com.bw.reference.dao.account.PortalAccountRepository;
import com.bw.reference.dao.account.PortalAccountTypeRoleRepository;
import com.bw.reference.domain.account.NewUserDto;
import com.bw.reference.domain.account.PortalAccountDto;
import com.bw.reference.domain.enumeration.EcadenceAdminRole;
import com.bw.reference.entity.PortalAccount;
import com.bw.reference.enums.PortalAccountTypeConstant;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.service.PortalAccountService;
import com.bw.reference.service.RoleService;
import com.bw.reference.service.UserRegistrationService;
import com.bw.enums.GenericStatusConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.util.Collections;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 */
public class PortalAccountSetup {

    @Autowired
    private Environment env;

    private final PortalAccountRepository portalAccountRepository;
    private final PortalAccountService portalAccountService;
    private final RoleService roleService;
    private final PortalAccountTypeRoleRepository portalAccountTypeRoleRepository;
    private final PortalAccountMembershipRepository portalAccountMembershipRepository;
    private final UserRegistrationService userRegistrationService;

    public PortalAccountSetup(
            PortalAccountRepository portalAccountRepository,
            PortalAccountService portalAccountService,
            RoleService roleService,
            PortalAccountTypeRoleRepository portalAccountTypeRoleRepository,
            PortalAccountMembershipRepository portalAccountMembershipRepository,
            UserRegistrationService userRegistrationService) {
        this.portalAccountRepository = portalAccountRepository;
        this.portalAccountService = portalAccountService;
        this.roleService = roleService;
        this.portalAccountTypeRoleRepository = portalAccountTypeRoleRepository;
        this.portalAccountMembershipRepository = portalAccountMembershipRepository;
        this.userRegistrationService = userRegistrationService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        for (EcadenceAdminRole ecadenceAdminRole : EcadenceAdminRole.values()) {
            portalAccountTypeRoleRepository.findActiveByPortalAccountTypeAndName(PortalAccountTypeConstant.APPLICATION, ecadenceAdminRole.name())
                    .orElseGet(() -> roleService.createRole(PortalAccountTypeConstant.APPLICATION, ecadenceAdminRole.name(), ecadenceAdminRole.roleName()));
        }
        PortalAccount portalAccount = portalAccountRepository.findFirstByTypeAndStatus(PortalAccountTypeConstant.APPLICATION, GenericStatusConstant.ACTIVE)
                .orElseGet(() -> {
                    PortalAccountDto dto = new PortalAccountDto();
                    dto.setType(PortalAccountTypeConstant.APPLICATION);
                    dto.setName(PortalAccountTypeConstant.APPLICATION.name());
                    return portalAccountService.createPortalAccount(dto, null);
                });
        portalAccountTypeRoleRepository.findActiveByPortalAccountTypeAndName(portalAccount.getType(), EcadenceAdminRole.ADMIN.name())
                .ifPresent(role -> {
                    if (portalAccountMembershipRepository.countActiveMemberships(portalAccount, role) == 0) {
                        NewUserDto newUserDto = new NewUserDto();
                        newUserDto.setUsername(env.getProperty("ecadence.default.admin.username"));
                        newUserDto.setEmail(env.getProperty("ecadence.default.admin.email"));
                        newUserDto.setFirstName(env.getProperty("ecadence.default.admin.firstname"));
                        newUserDto.setLastName(env.getProperty("ecadence.default.admin.lastname"));
                        newUserDto.setRoles(Collections.singletonMap(portalAccount.getCode(), Collections.singleton(role.getName())));
                        userRegistrationService.registerUserWithRoles(newUserDto);
                    }
                });
    }
}
