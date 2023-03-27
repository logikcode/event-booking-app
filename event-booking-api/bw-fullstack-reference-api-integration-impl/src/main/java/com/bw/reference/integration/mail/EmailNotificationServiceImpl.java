package com.bw.reference.integration.mail;

import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.integration.TemplateEngine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {
    public static final String VERIFY_EMAIL_TEMPLATE = "verify_email.ftl.html";
    public static final String GENERIC_NOTIFICATION_TEMPLATE = "generic_notification.ftl.html";

    @Value("${PLATFORM_NAME:ECADENCE}")
    @Getter
    private String platformName;


    @Value("${EMAIL_SENDER_NAME:ECadence Team}")
    @Getter
    private String emailSenderName;

    @Value("${EMAIL_ASSETS_BASE_URL}")
    @Getter
    private String emailassetsbaseurl;

    @Value("${DOMAIN_NAME}")
    @Getter
    private String domainName;


    @Override
    public void sendEmailVerification(WorkspaceUser portalUser) {
        Map<String, Object> bindings = new HashMap<>();

        //year, platformName, baseUrl, domainName

    }

    @Override
    public void sendForgotPassword(String email) {
        ///TODO
    }
}