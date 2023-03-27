package com.bw.reference.service;

import com.bw.commons.authclient.BwAuthApiClient;
import com.bw.commons.authclient.LoginType;
import com.bw.commons.authclient.dto.ApiResourcePortalUser;
import com.bw.commons.starter.SettingService;
import com.bw.enums.GenderConstant;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.dao.account.PortalAccountRepository;
import com.bw.reference.dao.account.PortalAccountTypeRoleRepository;
import com.bw.reference.dao.account.WorkspaceUserRepository;
import com.bw.reference.domain.account.NewUserDto;
import com.bw.reference.domain.account.PortalAccountDto;
import com.bw.reference.domain.account.SignUpResponse;
import com.bw.reference.domain.account.UserRegistrationDto;
import com.bw.reference.domain.enumeration.OrganisationAdminRole;
import com.bw.reference.domain.enumeration.UserTypeConstant;
import com.bw.reference.entity.*;
import com.bw.reference.enums.PortalAccountTypeConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;

@RequiredArgsConstructor
@Named
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final WorkspaceUserRepository workspaceUserRepository;
    private final PasswordService passwordService;
    private final BwAuthApiClient authClient;
    private final PortalAccountRepository portalAccountRepository;
    private final RoleService roleService;
    private final PortalAccountTypeRoleRepository portalAccountRoleRepository;
    private final PortalAccountMembershipService portalAccountMembershipService;
    private final PhoneNumberService phoneNumberService;
    private final SettingService settingService;
    private final KeycloakService keycloakService;
    private final PortalAccountService portalAccountService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    @Override
    public WorkspaceUser registerUserWithRoles(NewUserDto newUserDto) {
        WorkspaceUser portalUser = doUserRegistrationWithBwAccount(newUserDto);
        newUserDto.getRoles().entrySet().forEach(entry -> {
            PortalAccount portalAccount = portalAccountRepository.findActiveByCode(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("No portal account with code %s", entry.getKey())));
            PortalAccountMembership portalAccountMembership = portalAccountMembershipService.createMembership(portalAccount, portalUser);
            entry.getValue().forEach(roleName -> {
                PortalAccountTypeRole portalAccountRole = portalAccountRoleRepository.findActiveByPortalAccountTypeAndName(portalAccount.getType(), roleName)
                        .orElseThrow(() -> new IllegalArgumentException(String.format("Role %s not found for account type %s", roleName, portalAccount.getType().getValue())));
                roleService.addRole(portalAccountMembership, portalAccountRole);
            });
        });
        return portalUser;
    }

    @Override
    public WorkspaceUser createDefaultUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail(settingService.getString("DEF_ADMIN_USERNAME", "admineoladipupo@bwreference.com"));
        userRegistrationDto.setFirstName("Admin");
        userRegistrationDto.setLastName("Admin");
        userRegistrationDto.setMobileNumber("+2348130631033");
        userRegistrationDto.setGender(GenderConstant.MALE);
        userRegistrationDto.setUserType(UserTypeConstant.STAFF);
        userRegistrationDto.setOtpPin("");

        return createAdminUser(userRegistrationDto);
    }

    private WorkspaceUser createAdminUser(UserRegistrationDto userRegistrationDto) {

        boolean adminCreated = false;
        WorkspaceUser portalUser = new WorkspaceUser();

        if(userRegistrationDto.getWorkspaceUserId()!=null) {
            portalUser = workspaceUserRepository.findById(userRegistrationDto.getWorkspaceUserId()).orElseThrow(()-> new IllegalArgumentException(String.format("User with id %s not found!", userRegistrationDto.getWorkspaceUserId())));

        } else {

            String username = userRegistrationDto.getEmail();
            portalUser.setUsername(username);
            portalUser.setEmail(userRegistrationDto.getEmail());
            portalUser.setFirstName(userRegistrationDto.getFirstName());
            portalUser.setLastName(userRegistrationDto.getLastName());
            portalUser.setDisplayName(String.format("%s %s", userRegistrationDto.getFirstName(), userRegistrationDto.getLastName()));
            portalUser.setStatus(GenericStatusConstant.ACTIVE);
            if (userRegistrationDto.getGender() != null) {
                portalUser.setGender(userRegistrationDto.getGender());
            }
            if (StringUtils.isNotBlank(userRegistrationDto.getMobileNumber())) {
                String formattedMobileNumber = phoneNumberService.formatPhoneNumber(userRegistrationDto.getMobileNumber());
                workspaceUserRepository.findByEmailOrUsernameOrPhoneNumber(userRegistrationDto.getEmail(), username, formattedMobileNumber)
                        .ifPresent(p -> {
                            if (userRegistrationDto.getEmail().equals(p.getEmail())) {
                                throw new IllegalArgumentException((String.format("User with email %s already exists", p.getEmail())));
                            }
                            if (formattedMobileNumber.equals(p.getPhoneNumber())) {
                                throw new IllegalArgumentException(String.format("User with mobile Number %s already exists", p.getPhoneNumber()));
                            }
                            throw new IllegalArgumentException(String.format("User with username %s already exists", p.getUsername()));
                        });

                portalUser.setPhoneNumber(formattedMobileNumber);
            }
            if(StringUtils.isBlank(userRegistrationDto.getPassword())){
                userRegistrationDto.setPassword(passwordService.generatePassword());
                adminCreated = true;
                portalUser.setGeneratedPassword(userRegistrationDto.getPassword());
            }

            portalUser.setDateCreated(new Date());
            portalUser.setLastUpdated(new Date());
            SignUpResponse responsePojo = keycloakService.createNewUser(portalUser, userRegistrationDto.getPassword(),adminCreated);
            responsePojo.setWorkspaceUser(portalUser);
            portalUser.setUserId(responsePojo.getUserId());
            portalUser = workspaceUserRepository.save(portalUser);

        }

        PortalAccountDto portalAccountDto = new PortalAccountDto();
        portalAccountDto.setName(String.format("%s %s", userRegistrationDto.getFirstName(), userRegistrationDto.getLastName()));
        portalAccountDto.setEmail(userRegistrationDto.getEmail());
        portalAccountDto.setPhoneNumber(userRegistrationDto.getMobileNumber());
        portalAccountDto.setType(PortalAccountTypeConstant.APPLICATION);
        WorkspaceUser finalPortalUser = portalUser;
        PortalAccount portalAccount = portalAccountRepository.findFirstByTypeAndStatus(PortalAccountTypeConstant.APPLICATION,GenericStatusConstant.ACTIVE).orElseGet(()-> portalAccountService.createPortalAccount(portalAccountDto, null));

        PortalAccountMembership portalAccountMembership = portalAccountMembershipService.createMembership(portalAccount, finalPortalUser);
        PortalAccountTypeRole portalAccountRole = portalAccountRoleRepository.findFirstByAccountTypeAndNameAndStatus(portalAccount.getType(), OrganisationAdminRole.ADMIN.roleName(), GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Role %s not found for account type %s", OrganisationAdminRole.ADMIN.roleName(), portalAccount.getType().name())));
        roleService.addRole(portalAccountMembership, portalAccountRole);

        return finalPortalUser;
    }

    @Transactional
    @Override
    public WorkspaceUser doUserRegistrationWithBwAccount(NewUserDto newUserDto) {
        workspaceUserRepository.findByEmailOrUsername(newUserDto.getEmail(), newUserDto.getUsername())
                .ifPresent(portalUser -> {
                    if (newUserDto.getEmail().equals(portalUser.getEmail())) {
                        throw new IllegalArgumentException(String.format("User with email %s already exists", portalUser.getEmail()));
                    }
                    throw new IllegalArgumentException(String.format("User with username %s already exists", portalUser.getUsername()));
                });
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setUsername(newUserDto.getUsername() != null ? newUserDto.getUsername() : newUserDto.getEmail());
        workspaceUser.setEmail(newUserDto.getEmail());
        workspaceUser.setFirstName(newUserDto.getFirstName());
        workspaceUser.setLastName(newUserDto.getLastName());
        workspaceUser.setDisplayName(String.format("%s %s", newUserDto.getFirstName(), newUserDto.getLastName()));
        workspaceUser.setStatus(GenericStatusConstant.ACTIVE);
        if (!newUserDto.isFromSocialProvider()) {
            workspaceUser.setGeneratedPassword(passwordService.generatePassword());
        }
        if (StringUtils.isNotBlank(newUserDto.getPhoneNumber())) {
            workspaceUser.setPhoneNumber(phoneNumberService.formatPhoneNumber(newUserDto.getPhoneNumber()));
        }
        if (newUserDto.getGender() != null) {
            workspaceUser.setGender(newUserDto.getGender());
        }
        Date now = new Date();
        workspaceUser.setDateCreated(now);
        workspaceUser.setLastUpdated(now);
        SignUpResponse signUpResponse = newUserDto.isFromSocialProvider() ? getResponse(getUser(workspaceUser, null), workspaceUser) :
                getResponse(getUser(workspaceUser, workspaceUser.getGeneratedPassword()), workspaceUser);
        workspaceUser.setUserId(signUpResponse.getUserId());
        workspaceUserRepository.save(workspaceUser);

        return workspaceUser;
    }

    @Transactional
    @Override
    public SignUpResponse doUserRegistrationWithBwAccount(UserRegistrationDto userRegistrationDto) {
        String username = userRegistrationDto.getEmail();
        String formattedMobileNumber = phoneNumberService.formatPhoneNumber(userRegistrationDto.getMobileNumber());
        workspaceUserRepository.findByEmailOrUsernameOrPhoneNumber(userRegistrationDto.getEmail(), username, formattedMobileNumber)
                .ifPresent(workspaceUser -> {
                    logger.info("===> User already exists {}", workspaceUser.getUsername());
                    if (userRegistrationDto.getEmail().equals(workspaceUser.getEmail())) {
                        throw new IllegalArgumentException(String.format("User with email %s already exists", workspaceUser.getEmail()));
                    }
                    if (formattedMobileNumber.equals(workspaceUser.getPhoneNumber())) {
                        logger.info("====> phone number already exists {}", formattedMobileNumber);
                        throw new IllegalArgumentException(String.format("User with mobile Number %s already exists", workspaceUser.getPhoneNumber()));
                    }
                    throw new IllegalArgumentException(String.format("User with username %s already exists", workspaceUser.getUsername()));
                });
        WorkspaceUser portalUser = new WorkspaceUser();
        portalUser.setUsername(username);
        portalUser.setEmail(userRegistrationDto.getEmail());
        portalUser.setFirstName(userRegistrationDto.getFirstName());
        portalUser.setLastName(userRegistrationDto.getLastName());
        portalUser.setDisplayName(String.format("%s %s", userRegistrationDto.getFirstName(), userRegistrationDto.getLastName()));
        portalUser.setPhoneNumber(formattedMobileNumber);
        portalUser.setStatus(GenericStatusConstant.ACTIVE);
        if (userRegistrationDto.getGender() != null) {
            portalUser.setGender(userRegistrationDto.getGender());
        }
        Date now = new Date();
        portalUser.setDateCreated(now);
        portalUser.setLastUpdated(now);

        ApiResourcePortalUser apiResourcePortalUser = getUser(portalUser, userRegistrationDto.getPassword());

        SignUpResponse response = getResponse(apiResourcePortalUser, portalUser);
        response.setWorkspaceUser(portalUser);
        portalUser.setUserId(response.getUserId());
        workspaceUserRepository.save(portalUser);

        return response;
    }

    @Transactional
    @Override
    public WorkspaceUser verifyEmail(WorkspaceUser portalUser) {
        portalUser.setEmailVerified(true);
        workspaceUserRepository.save(portalUser);
        return portalUser;
    }


    private ApiResourcePortalUser getUser(WorkspaceUser portalUser, String password) {
        ApiResourcePortalUser user = new ApiResourcePortalUser();
        user.setFirstName(portalUser.getFirstName());
        user.setLastName(portalUser.getLastName());
        user.setEmail(portalUser.getEmail());
        user.setPhoneNumber(portalUser.getPhoneNumber());
        user.setPassword(password);
        user.setUsername(portalUser.getEmail());
        user.setLoginType(LoginType.EMAIL);
        return user;
    }

    private SignUpResponse getResponse(ApiResourcePortalUser dto, WorkspaceUser portalUser) {
        ApiResourcePortalUser createdUser = authClient.createUser(dto);
        portalUser.setUserId(createdUser.getUserId());
        workspaceUserRepository.save(portalUser);

        SignUpResponse responsePojo = new SignUpResponse();
        responsePojo.setWorkspaceUser(portalUser);
        responsePojo.setAuthToken(createdUser.getAuthToken());
        responsePojo.setUserId(createdUser.getUserId());
        return responsePojo;
    }
}
