package com.bw.reference.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gibah Joseph
 * email: gibahjoe@gmail.com
 * Aug, 2020
 **/
@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Getter
@Setter
public class AppConfigurationProperties {
    private String authBaseUrl;
    private String reportApiBaseUrl;
    private String frontendBaseUrl;
    /**
     * Path to the directory where to find email templates
     */
    private String emailTemplatesDirectory;
    /**
     * This is the base url that will be used when contacting file server on the local network
     */
    private String fileServerLocalBaseUrl;
    /**
     * Secret key for communication with nvs
     */
    private String middlewareSecretKey;

    private String twilioAccountSSID;

    private String twilioAuthToken;

    private String firebaseDynamicLinkBaseUrl;
    private String firebaseApiKey;
    private String androidAppPackageName;
    private String iosAppBundleId;
    private String dynamicLinksDomainUriPrefix;
    private String metabaseSiteUrl;
    private String metabaseSecretKey;
    private String keystorePath;
    /**
     * This is the secret key to be used for all aes encryptions
     */
    private String encryptionSecretKey;
}
