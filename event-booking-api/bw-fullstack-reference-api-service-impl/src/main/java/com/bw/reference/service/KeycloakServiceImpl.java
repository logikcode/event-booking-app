package com.bw.reference.service;

import com.bw.reference.constant.Scope;
import com.bw.reference.domain.AuthUserDto;
import com.bw.reference.domain.account.NewUserDto;
import com.bw.reference.domain.account.SignUpResponse;
import com.bw.reference.entity.WorkspaceUser;
import com.google.gson.annotations.SerializedName;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientScopeRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Gibah Joseph
 * email: gibahjoe@gmail.com
 * Aug, 2020
 **/
@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    //    private static final Logger logger = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    private final RealmResource realmResource;
    private final Keycloak keycloak;
    private final ClientResource clientResource;

    @Inject
    private KeycloakConfigurationProperties keycloakConfigurationProperties;

    @Override
    public AuthUserDto createNewUser(NewUserDto user) {
        List<UserRepresentation> userRepresentations = realmResource.users().search(user.getUsername(), true);
        userRepresentations.addAll(realmResource.users().search(null, null, null, user.getEmail(), 0, 1));
        if (userRepresentations.isEmpty()) {
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setUsername(user.getUsername());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(false);

            Response response = realmResource.users().create(userRepresentation);
//            logger.info("===> Create user return with status {}", response.getStatus());
            System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
            if (response.getStatus() > 300) {
                return null;
            }
            String userId = CreatedResponseUtil.getCreatedId(response);


            UserResource userResource = realmResource.users().get(userId);
            if (StringUtils.isNotBlank(user.getPassword())) {
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());
                credentialRepresentation.setTemporary(user.isAdminCreated());
                userResource.resetPassword(credentialRepresentation);
            }
            UserRepresentation userRep = userResource.toRepresentation();
            //TODO: Make email send from api.
//            userResource.sendVerifyEmail();
            AuthUserDto authUserDto = new AuthUserDto();
            authUserDto.setId(userRep.getId());
            authUserDto.setUserName(userRep.getUsername());
            return authUserDto;
        }


        UserRepresentation userRepresentation = userRepresentations.get(0);
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId(userRepresentation.getId());
        authUserDto.setUserName(userRepresentation.getUsername());
        return authUserDto;
    }

    @Override
    public void changePassword(String newPassword, WorkspaceUser portalUser) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newPassword);
        credentialRepresentation.setTemporary(false);
        UsersResource users = realmResource.users();
        portalUser.getUserId();
        users.count();
        UserResource userResource = users.get(portalUser.getUserId());
        userResource.resetPassword(credentialRepresentation);
    }


    @Override
    public void logout(WorkspaceUser portalUser) {
        realmResource.users().get(portalUser.getUserId()).logout();
    }

    @Override
    public void createDefaultScopes() {
        List<ClientScopeRepresentation> clientScopeRepresentations = realmResource.clientScopes().findAll();
        for (Scope value : Scope.values()) {
            if (clientScopeRepresentations.stream().noneMatch(clientScopeRepresentation -> clientScopeRepresentation.getId().equalsIgnoreCase(value.getCode()))) {
                ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();
                clientScopeRepresentation.setId(value.getCode());
                clientScopeRepresentation.setName(value.name());
                clientScopeRepresentation.setDescription(value.getDescription());

                realmResource.clientScopes().create(clientScopeRepresentation);
            }
        }
    }


    @Override
    public SignUpResponse createNewUser(WorkspaceUser portalUser, String password, boolean adminCreated) {
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setUsername(portalUser.getUsername());
        newUserDto.setPassword(password);
        newUserDto.setEmail(portalUser.getEmail());
        newUserDto.setPhoneNumber(portalUser.getPhoneNumber());
        newUserDto.setFirstName(portalUser.getFirstName());
        newUserDto.setLastName(portalUser.getLastName());
        newUserDto.setGender(portalUser.getGender());
        newUserDto.setAdminCreated(adminCreated);

        AuthUserDto newUser = createNewUser(newUserDto);
        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setUserId(newUser.getId());
        signUpResponse.setAuthToken(null);
        signUpResponse.setWorkspaceUser(portalUser);
        return signUpResponse;
    }



    @Override
    public String generateAccessToken(String username, String password, String clientId) {
        try (Keycloak keycloak = Keycloak.getInstance(keycloakConfigurationProperties.getAuthUrl(),// keycloak address
                "bwreference",
                username,
                password, clientId)) {
            return keycloak.tokenManager().getAccessToken().getToken();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Something went wrong");
        }

    }

    public static class TokenResponse {
        @SerializedName("access_token")
        public String accessToken;
    }
}
