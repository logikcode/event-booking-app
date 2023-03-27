package com.bw.reference.configuration;

import com.bw.reference.service.AppConfigurationProperties;
import com.bw.reference.service.KeycloakConfigurationProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Neme Iloeje
 * email: niloeje@byteworks.com.ng
 * May, 2021
 **/
@Configuration
@Profile("!test")
public class KeycloakConfiguration {

    private final KeycloakConfigurationProperties keycloakConfigurationProperties;
    private final AppConfigurationProperties appConfigurationProperties;


    public KeycloakConfiguration(KeycloakConfigurationProperties keycloakConfigurationProperties, AppConfigurationProperties appConfigurationProperties) {
        this.keycloakConfigurationProperties = keycloakConfigurationProperties;
        this.appConfigurationProperties = appConfigurationProperties;
    }

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(keycloakConfigurationProperties.getAuthUrl(),// keycloak address
                "master", // specify Realm master
                keycloakConfigurationProperties.getAdminUserName(), // administrator account
                keycloakConfigurationProperties.getAdminPassword(), // administrator password
                // Specify the client (admin-cli is the built-in client in Master Realm, Direct Access
                // Grants Enabled）
                "admin-cli");
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakConfigurationProperties.getRealm());
    }

    @Bean
    public ClientResource clientResource(RealmResource realmResource) {
        return realmResource.clients().get(keycloakConfigurationProperties.getClientId());
    }
}

