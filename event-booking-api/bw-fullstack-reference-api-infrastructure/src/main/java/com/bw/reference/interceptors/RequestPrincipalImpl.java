package com.bw.reference.interceptors;

        import com.bw.enums.GenericStatusConstant;
        import com.bw.reference.dao.AppRepository;
        import com.bw.reference.dao.account.PortalAccountMembershipRepository;
        import com.bw.reference.dao.account.PortalAccountTypeRoleRepository;
        import com.bw.reference.dao.account.WorkspaceUserRepository;
        import com.bw.reference.domain.account.AccountMembershipPojo;
        import com.bw.reference.entity.PortalAccount;
        import com.bw.reference.entity.PortalAccountMembership;
        import com.bw.reference.entity.PortalAccountTypeRole;
        import com.bw.reference.entity.WorkspaceUser;
        import com.bw.reference.principal.RequestPrincipal;
        import com.bw.reference.service.PortalAccountMembershipService;
        import lombok.extern.slf4j.Slf4j;
        import org.apache.commons.lang3.StringUtils;
        import org.keycloak.KeycloakPrincipal;
        import org.keycloak.KeycloakSecurityContext;
        import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
        import org.keycloak.representations.AccessToken;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Profile;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.support.TransactionTemplate;
        import org.springframework.web.context.annotation.RequestScope;

        import javax.servlet.http.HttpServletRequest;
        import javax.transaction.Transactional;
        import java.security.Principal;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

/**
 * @author Olaleye Afolabi <oafolabi@byteworks.com.ng>
 */
@RequestScope
@Service
@Slf4j
@Profile("!test")
public class RequestPrincipalImpl implements RequestPrincipal {
    private final String ipAddress;
    private WorkspaceUser workspaceUser;
    private PortalAccount portalAccount;

    private final TransactionTemplate transactionTemplate;

    private AccessToken accessToken;
    private final PrincipalType principalType;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final PortalAccountMembershipRepository membershipRepository;
    private final PortalAccountTypeRoleRepository portalAccountTypeRoleRepository;

    private final AppRepository appRepository;

    private List<AccountMembershipPojo> accountMembership;
    private final PortalAccountMembershipService membershipService;
    private final String accountCode;


    @Autowired
    public RequestPrincipalImpl(HttpServletRequest request, TransactionTemplate transactionTemplate, WorkspaceUserRepository workspaceUserRepository,
                                PortalAccountMembershipRepository membershipRepository,
                                PortalAccountTypeRoleRepository portalAccountTypeRoleRepository, AppRepository appRepository, PortalAccountMembershipService membershipService) {
        this.portalAccountTypeRoleRepository = portalAccountTypeRoleRepository;
        this.appRepository = appRepository;
        this.transactionTemplate = transactionTemplate;
        this.membershipService = membershipService;
        Principal principal = request.getUserPrincipal();
        this.ipAddress = StringUtils.defaultIfBlank(request.getHeader("X-FORWARDED-FOR"), request.getRemoteAddr());
        this.workspaceUserRepository = workspaceUserRepository;
        this.membershipRepository = membershipRepository;
        this.accountCode = request.getHeader("X-ACCOUNT-CODE");
        if (principal == null) {
            this.principalType = PrincipalType.GUEST;
        } else {
            KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
            KeycloakPrincipal<KeycloakSecurityContext> p = (KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal();
            KeycloakSecurityContext sessionContext = p.getKeycloakSecurityContext();
            this.accessToken = sessionContext.getToken();
            this.principalType = PrincipalType.USER;

        }
    }


    @Override
    public String getUserId() {
        if (principalType == PrincipalType.GUEST) {
            return null;
        }
        return this.accessToken.getSubject();
    }

    @Transactional
    @Override
    public WorkspaceUser getWorkspaceUser() {
        if (principalType == PrincipalType.GUEST) {
            return null;
        }
        if (this.workspaceUser == null) {
            this.workspaceUser = workspaceUserRepository.findByUserId(getUserId()).orElse(null);
        }
        return this.workspaceUser;
    }

    @Transactional
    @Override
    public List<AccountMembershipPojo> getAccountPermissions() {
        if (principalType == PrincipalType.GUEST) {
            return null;
        }
        WorkspaceUser workspaceUser = getWorkspaceUser();
        if (accountMembership == null) {
            accountMembership = membershipService.getAllMemberships(workspaceUser);
        }
        return accountMembership;
    }


    @Override
    public String getUserName() {
        return this.accessToken.getPreferredUsername();
    }


    @Override
    public String getDisplayName() {
        if (getWorkspaceUser() == null) {
            return null;
        }
        return this.workspaceUser.getDisplayName();
    }
    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public List<PortalAccountTypeRole> getRoles() {
        return portalAccountTypeRoleRepository.findAllByPortalUser_UserIdAndStatus(getUserId(), GenericStatusConstant.ACTIVE);
    }



}
